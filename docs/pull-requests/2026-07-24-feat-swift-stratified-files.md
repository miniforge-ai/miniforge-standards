# feat: Swift stratified-files rule (dewey 241)

## Overview

Adds `languages/swift-stratified-files.mdc` (241) — the Swift member of
the per-file Layer-heading family (Clojure 210, Python 221, Rust 236),
with the strict semantics (SL008 same-layer calls, SL009 over-placed
types) from the start.

## Motivation

swift.mdc (240) mandates cross-file L0-L3 decomposition; 241 makes the
within-file strata explicit and lintable. The Swift-specific insight the
rule codifies: member access is not a reference edge, so a god-class
with headings is decoration — the discipline requires pure logic in
top-level functions (which 240 already prescribes) with stateful shells
delegating.

## Changes in Detail

- New rule 241: marker, strict semantics, what layering governs in
  Swift (top-level declarations; extensions as the `impl` analog),
  enforcement via stratum-lint's Swift implementation
  (miniforge-ai/stratum-lint#4), exemplar reference
  (miniforge-ai/thesium#414 — DashboardViewModel split, 197 tests
  unmodified).
- `index.mdc` + `CLAUDE.md` catalog/quick-reference entries.

## Testing Plan

Docs-only. Codes and invocation verified against the shipped linter
(PR #4, CI green all four language jobs); exemplar verified (all files
strict-lint clean).

## Deployment Plan

Merges to main; consumers pick it up on their next `.standards` bump.

## Related Issues/PRs

- miniforge-ai/stratum-lint#4 (Swift linter, merged CI-green)
- miniforge-ai/thesium#414 (exemplar)
- Companions: #82 (221), #83 (210 ref), #85 (236 — merged)

## Checklist

- [x] Rule follows `meta/rule-format` structure
- [x] index/CLAUDE cross-references updated
- [x] Linter and exemplar shipped before the rule references them
