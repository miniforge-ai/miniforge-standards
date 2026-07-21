# feat: Rust stratified-modules rule (dewey 236)

## Overview

Adds `languages/rust-stratified-modules.mdc` (236) — the Rust member of
the stratified-design family (Clojure 210, Python 221): `// ---- Layer N`
comment headings per file, references only at or below the item's own
layer, ≤3 layers per file, thin index over bounded-operation modules.

## Motivation

Rules 210/221 are linter-enforced; Rust deferred to foundations 001 with
no per-file convention. Both halves now exist: the linter
(stratum-lint#3, `stratum-lint-rs`, syn-based) and a real exemplar
(minibench#20 — the 2,454-line kernel `lib.rs` split into five
Layer-headed modules, public API unchanged, behavior tests unmodified,
linter clean).

## Changes in Detail

- New rule `languages/rust-stratified-modules.mdc`: marker rationale
  (heading over `#[stratum]` attribute — no proc-macro dep on
  consumers), file anatomy (thin index + shared-vocabulary leaf),
  linter codes and limitations, exemplar reference, good/invalid
  examples.
- `languages/rust.mdc`: sister-rules entry for 236.
- `index.mdc` + `CLAUDE.md`: tree, table, quick-reference, Dewey range.

## Testing Plan

Docs-only. Codes and CLI verified against the shipped linter; exemplar
claims verified against minibench#20 (48 tests green, clippy clean,
lint exit 0).

## Deployment Plan

Merges to `main`; consumers pick it up on their next `.standards` bump.

## Related Issues/PRs

- miniforge-ai/stratum-lint#3 (linter), miniforge-ai/minibench#20 (exemplar)
- Companions: #82 (Python 221), #83 (Clojure 210 enforcement ref)

## Checklist

- [x] Rule follows `meta/rule-format` structure
- [x] index/CLAUDE/rust.mdc cross-references updated
- [x] Linter and exemplar shipped before the rule references them
