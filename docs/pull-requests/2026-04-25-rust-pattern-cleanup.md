# feat(languages/rust): boundary commands, pipeline selection, data-driven content

## Overview

Adds three new sections to `languages/rust.mdc` (dewey 230) capturing patterns
extracted from a cross-repo Rust pattern review (`miniforge-control`,
`risk-dashboard/theseus-engine`, `risk-dashboard/thesium-data-plane`,
`thesium-career/theseus-engine`, `thesium-career/thesium-data-plane`).
No new files. Six new entries in the existing "What NOT to do" list make the
rejections explicit. `index.mdc` gains the previously-missing Rust (230) and
Swift (240) entries.

## Motivation

Three patterns were observed to be either absent across the Rust workspaces or
present in inconsistent forms, with concrete failure modes that have already
shown up in code:

1. **Stringly-typed boundary dispatch.** `risk-dashboard` facade routes via
   `match envelope.command.as_str()` with an `_ => error("unknown command")`
   arm. The set of legal commands is duplicated between the Rust facade, the
   Swift caller, and schema docs; adding a command produces no compile error
   anywhere; parameter decoding is scattered across handlers as
   `params.get("...").and_then(|v| v.as_str())` chains.

2. **Phase / mode / tier branching scattered through stages.**
   `thesium-career/theseus-engine/data-foundry` defines a `Stage` trait but no
   composition shape; the planned A/A'..H/H'' pipeline has known modes (open
   vs. local-only inference, tier-conditional behavior) that, without a
   selector enum at the top, will leak into every stage as `if ctx.policy ==
   ...` checks.

3. **Tunable content baked into Rust literals.** `risk-dashboard` has a
   36-entry `default_registry()` of `SourceEntry` literals describing FRED
   metric thresholds, families, and clusters. Tuning a threshold requires a
   Rust release, and the table is not legible to non-engineers.

A separate companion RFC (`miniforge.ai/RFC-rust-pattern-cleanup.md`) describes
the per-repo engineering work to apply these rules to the existing codebases.
This PR is the standards-side change only.

## Changes in Detail

### `languages/rust.mdc` — new section "Boundary commands and pipeline selection"

Inserted after "Type idioms" and before "`impl Trait` vs generics vs `dyn Trait`".
Three subsections, each with positive and negative examples and a short
"Reasons" list:

- **Typed boundary commands** — a boundary that accepts more than one
  operation MUST express the operation set as an enum in `contracts`; the
  dispatch site MUST be an exhaustive `match` over that enum with no `_` arm;
  wire formats are decoded into the typed enum at the boundary, once.
- **Pipeline selection by enum** — when a phase, mode, tier, or policy
  determines which sequence of pure functions runs, encode the selector as an
  enum and define one pipeline function per variant; stages themselves do not
  branch on the selector.
- **Bounded data-driven content** — content tuned by domain owners
  (thresholds, weights, lens templates) lives in RON/TOML under
  `<crate>/resources/`, loaded once via `OnceLock` + `include_str!`;
  structural identifiers referenced from code (cluster IDs, source-type tags,
  schema-version strings) stay as `pub const &'static str`.

The heuristic line for the third subsection: *"if a non-engineer would
reasonably want to edit the value without a Rust release, it is content;
move it. If editing it requires changes to handlers that key on it, it is
structure; keep it as `const`."*

### `languages/rust.mdc` — six new "What NOT to do" entries

- Do not dispatch boundary commands by `match s.as_str()`.
- Do not branch on phase / mode / tier / policy inside individual stage
  functions.
- Do not adopt an ECS (legion, bevy_ecs, hecs, specs) for service code;
  reserve ECS for genuine real-time simulation; if it appears, prefer Bevy.
- Do not encode "capability by component presence"; use Rust typestate.
- Do not add a crate-wide `prelude` module that pub-uses everything.
- (No change to existing entries.)

The ECS / capability-by-presence / prelude rejections are explicit because
the source pattern (a small Hands-on-Rust legion roguelike) tempts adoption
of those patterns alongside the three useful ones; making the rejection
explicit prevents that drift.

### `index.mdc` — backfill missing entries

The `languages/` table previously listed Clojure (210) and Python (220) only.
The Rust (230) and Swift (240) `.mdc` files exist on disk but were not in
the index. This PR adds both rows. The Rust description references the new
sections; the Swift description matches the existing file's frontmatter.

## Failure modes prevented

- **Compile-error-free addition of a new boundary command.** A new variant
  added to the typed `Command` enum produces a non-exhaustive-match error in
  every dispatch site until handled. The string-keyed match cannot do this.
- **Stage signatures that drift to take a `Phase` / `Tier` / `Policy`
  argument.** Once that drift starts, every downstream stage acquires the
  argument transitively. The selector-at-the-top rule kills the drift at the
  shape level.
- **Recompile-to-tune content tables.** A threshold edit becoming a Rust
  release is a real cost paid every quarter when domain owners want to adjust
  signal thresholds.
- **Drift toward an ECS-shaped service.** Without an explicit rejection,
  agents reading the companion RFC may see "ilhaven good" and propose
  capability-by-presence in service code. The explicit "What NOT to do"
  entries prevent that.

## Verification

A reviewer or agent verifies conformance by:

- Searching the consuming Rust workspace for `match.*as_str\(\)` inside files
  named `dispatch.rs` or `route.rs`. Any match is a violation.
- Searching for `if.*ctx\.(phase|mode|tier|policy)` inside per-stage modules.
  Matches are violations; the same conditional belongs in the pipeline
  function for the relevant variant.
- For new content tables: confirming the table is in `resources/*.{toml,ron}`
  rather than as a `Vec<...>` literal in code, with a `OnceLock` loader and a
  validation test.

## Downstream impact

The companion RFC (`miniforge.ai/RFC-rust-pattern-cleanup.md`) is the
implementation-side change list. Repos affected when that work lands:

- `miniforge-control` — already conforms (typed `ParsedEvent`, single-writer
  state, `Mode` enum picks render path). Cited as the reference shape.
- `risk-dashboard/theseus-engine` — facade dispatch refactor, registry move
  to TOML.
- `thesium-career/theseus-engine` — pipeline selector enum at scaffold time,
  before stages are written.
- `thesium-career/thesium-data-plane`, `thesium-data-plane` (risk) — apply
  the rules to any new boundary added; no existing surface to refactor.

No CI or build changes. No new dependencies introduced into consumers.

## Checklist

- [x] Frontmatter unchanged on `languages/rust.mdc` (existing dewey 230).
- [x] `index.mdc` updated.
- [x] `CLAUDE.md` already lists `languages/rust` and `languages/swift`; no
      change needed.
- [x] Both positive and negative examples included for each new subsection.
- [x] Diff under 400 lines (rust.mdc +148, index.mdc +2 lines).
- [x] Pre-commit hooks pass without `--no-verify`.
