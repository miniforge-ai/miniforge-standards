# feat(languages/rust): ratify miniforge-control patterns — context assembly, thin index modules, test_support

## Overview

Stacks on top of [`feat/rust-pattern-cleanup-standards`](./2026-04-25-rust-pattern-cleanup.md).
Codifies three patterns proven out in `miniforge-control` into `languages/rust.mdc` (dewey
230). Source: `RFC-rust-pattern-ratifications.md` in the consuming workspace.

These three patterns are *ratifications* of existing miniforge-control practice, not new
prescriptive refactors. They are introduced as Rust mechanics on top of foundations rules
that already cover the underlying principles.

## Motivation

A cross-repo Rust pattern review identified six patterns. Three are forward-looking and
shipped in the parent PR (typed boundary commands, pipeline selection, data-driven
content registries). The remaining three are backward-looking — they describe shapes that
miniforge-control already exhibits and have proven robust there:

1. **Derived context assembly at boundary seams** —
   `tui/src/ui/detail/dossier.rs::RunDossier` and `tui/src/operator_attention.rs::OperatorActionItem`
   are immutable derived views assembled once at the seam, consumed by sibling renderers
   without rescanning root state.
2. **Thin orchestration index modules** — `tui::ui::*`, `tui::app::*`, and
   `state::projections::*` are split by bounded surface; the index files (`<name>.rs`)
   route, delegate, and re-export rather than accreting helpers across surfaces.
3. **`test_support` modules with `pub(crate)` factories** —
   `state/src/test_support.rs` and `tui/src/test_support.rs` own canonical valid fixtures;
   tests override only the scenario-relevant fields via struct-update syntax.

The underlying principles for #2 (no god modules) and #3 (factory functions, tests-as-
production-code) are already covered in `foundations/stratified-design`,
`foundations/code-quality`, and `testing/standards`. This PR adds only the **Rust-specific
mechanics** on top — index-file shape, `pub(crate)` visibility, struct-update overrides —
without re-stating the underlying rules.

The Pattern 1 (derived context) trigger is intentionally narrow: only when the response or
surface shape spans ≥ 2 entity families AND ≥ 2 sibling consumers would otherwise repeat
the same correlation logic. This avoids universalizing a pattern that is overkill for
trivial CRUD responses.

## Changes in Detail

### `languages/rust.mdc` — three new sections

- **§ Crate and module structure → Thin orchestration index modules** — index file
  routes/delegates/re-exports; submodules split by bounded concern; no `utils.rs` /
  `helpers.rs` / `common.rs` dumping grounds. Underlying principle is referenced back to
  `foundations/stratified-design` and `foundations/code-quality`; this section is the
  Rust-mechanic addendum only.
- **§ Boundary commands and pipeline selection → Derived context at boundary seams** —
  defines the trigger, the rule, the constraints (builder is pure, join policy lives in
  one place, downstream takes the context not root state, contexts stay boundary-local),
  and the explicit god-context rejection.
- **§ Testing → `test_support` modules** — `pub(crate)` factory functions, struct-update
  overrides, placement (`test_support.rs` at crate root vs `#[cfg(test)] mod`), and the
  rule for when cross-crate fixture sharing earns a separate test-support workspace
  member.

### `languages/rust.mdc` — four new "What NOT to do" entries

- Do not create `utils.rs` / `helpers.rs` / `common.rs` dumping grounds.
- Do not let an index module own helpers across surfaces.
- Do not promote a derived context object into domain state, contract DTOs, or cross-crate
  types.
- Do not hand-build the same entity shape across test modules.

## Failure modes prevented

- **Drifted joins between sibling renderers.** Today, two renderers in the same boundary
  can each apply slightly different correlation rules (one filters by `correlation_id`,
  another by `workflow_key`). The derived-context rule forces the join into one builder
  function with one set of rules.
- **`utils.rs` accretion.** Without an explicit name-the-bounded-concern rule, helper
  modules become unbounded dumping grounds and split the wrong way later.
- **Index modules owning everything.** Without the route/delegate/re-export discipline,
  `mod.rs` and `<name>.rs` files become 1000+ line catch-all files even when the surface
  has been "split" into submodules — because all logic lives at the index.
- **Test fixture drift.** Without `test_support`, every test file hand-builds the same
  `WorkflowRun`, `PrFleetEntry`, etc.; field defaults diverge across files; a contract
  change becomes a repo-wide mechanical sweep.
- **God-context.** The explicit rejection in the derived-context section prevents the
  pattern from degrading into "one big context object holds everything for every surface".

## Verification

A reviewer or agent verifies conformance by:

- For derived context: check that boundaries with composite responses have a single
  builder function returning a typed context struct, and that downstream consumers take
  `&Context` rather than `&RootState`. Check that the context is private to the boundary
  crate / module.
- For thin index modules: open each `mod.rs` / index file. Confirm it primarily contains
  `mod`, `pub use`, and a small dispatch / orchestration function. Search for files named
  `utils.rs`, `helpers.rs`, `common.rs` — any match is a violation.
- For `test_support`: search the crate for `WorkflowRun {` (or whichever entity recurs)
  inside test modules. The first occurrence per crate may be in `test_support.rs`; further
  occurrences should be `..test_support::workflow_run(id)` struct-update overrides, not
  full literals.

Each new section in `languages/rust.mdc` includes a positive and a negative example so the
check is mechanical.

## Downstream impact

- **`miniforge-control`** — already conforms; this PR cites it as the reference shape.
  No work required.
- **`risk-dashboard/theseus-engine`, `risk-dashboard/thesium-data-plane`,
  `thesium-career/theseus-engine`, `thesium-career/thesium-data-plane`** — apply the rules
  when next adding a composite boundary, splitting a growing index module, or hand-building
  the same fixture in a second test file. No present-day candidates for retroactive
  refactor.

No new dependencies. No CI / build changes.

## Stacking note

Base branch is `feat/rust-pattern-cleanup-standards` (PR #10), not `main`. The "Derived
context at boundary seams" section is inserted under the parent PR's new "Boundary
commands and pipeline selection" header, and the new "What NOT to do" entries follow the
parent PR's additions. Ship order: parent first, then this PR. After parent merges to main,
GitHub will offer to retarget this PR to `main`; accept that retarget once it's live.

## Checklist

- [x] Frontmatter unchanged on `languages/rust.mdc` (existing dewey 230).
- [x] `index.mdc` description string for rust (230) updated by the parent PR remains
      accurate; no additional change needed.
- [x] `CLAUDE.md` quick-reference already lists `languages/rust`; no change needed.
- [x] Both positive and negative examples included for each new subsection.
- [x] PR is under ~400 lines of diff (140 insertions, 1 file).
- [x] Pre-commit hooks pass without `--no-verify`.
- [x] Living PR doc at `docs/pull-requests/2026-04-25-rust-pattern-ratifications.md`.
