# feat: add exceptions-as-data foundation rule (dewey 005)

## Overview

Adds a new foundation rule, `foundations/exceptions-as-data.mdc` (dewey `005`, `alwaysApply: true`), and updates the `index.mdc` catalog to include it. The rule formalizes the discipline that operations returning failure should produce **anomaly maps as data**, not throw exceptions. Throwing is restricted to absolute process boundaries and programmer-error guards.

## Motivation

Miniforge-family Clojure code has regressed on this discipline — primarily because LLM-trained code generation defaults to idiomatic-Java exception-throwing. Without a standing rule in the standards pack, drift recurs every few months.

A companion inventory landing in miniforge OSS (`work/exception-cleanup-inventory.md`) identifies 158 cleanup-needed throw sites in production source. A new foundational primitive (`ai.miniforge.anomaly`) is being added to miniforge OSS to provide the canonical anomaly shape. This rule is the *standing* enforcement: it documents the discipline, names the boundary patterns, prescribes the anomaly shape, and specifies the linter design.

It pairs with two existing foundation rules:

- `003 result-handling` — defines the result-map shape and predicates (success?/failed?). This rule extends that discipline to error propagation specifically.
- `004 validation-boundaries` — defines *where* validation occurs. This rule defines *how errors propagate* from those validation points and elsewhere.

## Base Branch

`main`

## Depends On

- The canonical `ai.miniforge.anomaly` component must land in miniforge OSS for the linter implementation. The rule itself can land first; the linter is gated on the component.

## Layer

Foundations / `alwaysApply: true`. Joins the core sequence (`001` stratified-design → `002` code-quality → `003` result-handling → `004` validation-boundaries → **`005` exceptions-as-data** → `010` simple-made-easy).

## What This Adds

- `foundations/exceptions-as-data.mdc` — full rule:
  - Statement of the rule and its two narrow exemptions (boundary, programmer-error)
  - Three reasons for data-first errors (data preservation, parallel composition, provenance)
  - Canonical anomaly shape and standard type vocabulary (mirrors cognitect anomalies)
  - Boundary namespace patterns table (`*.cli.*`, `*.boundary.*`, `*.http.*`, `*.mcp.*`, `*.consumer.*`, `*-main`, `execute-with-exception-handling` helpers)
  - Examples: violation, correction, acceptable boundary catch, acceptable programmer-error guard
  - Agent behavior section
  - Anti-patterns
  - **Linter design** — implementation lives in miniforge OSS `bb review`; namespace-pattern matching, severity defaults to `:warning`, gated on the canonical anomaly component
  - References to related rules (`001`, `003`, `004`)
- `index.mdc`:
  - Directory-structure tree updated to include `exceptions-as-data.mdc`
  - `Current Rules > foundations` table updated with the new row

## Strata Affected

- `foundations/exceptions-as-data.mdc` — new rule
- `index.mdc` — catalog updates (tree + table)

No other rules modified.

## Testing Plan

This is a documentation-only rule pack. Validation is structural:

- New rule file follows the format of sibling foundation rules (`stratified-design.mdc`, `result-handling.mdc`, `validation-boundaries.mdc`):
  - MDC frontmatter (`dewey`, `description`, `alwaysApply`)
  - Title with `(ALWAYS)` suffix matching sibling convention
  - "The rule" / "Why" / examples / "Agent behavior" / "Anti-patterns" sections
- `index.mdc` updates align with existing tree and table conventions
- Markdown renders cleanly; no broken anchors

There is no `bb test` task in this repo (no `bb.edn`); the standards pack is consumed as a `.standards/` submodule by other repos.

## Deployment Plan

No migration. Additive rule.

Once merged, downstream consumers re-pulling the standards submodule will pick up the new rule. The linter is *not* enforced until:

1. The `ai.miniforge.anomaly` component lands in miniforge OSS.
2. The miniforge `bb review` linter implementation is shipped (separate workstream).
3. Per-repo cleanup brings the regression count toward zero.

Severity at landing: `:warning`. Promotion to `:error` happens per-repo after the cleanup completes.

## Notes

- **No Clojure scripts in this repo.** miniforge-standards is documentation-only — rules are `.mdc` files consumed downstream as the canonical reference. The linter implementation lives in miniforge OSS where `bb review` runs. The "Linter design" section of the rule documents the design; the code lands separately.
- **Dewey choice:** `005` slots cleanly into the foundations sequence (`001`–`004` are the always-applied core principles; `005` continues that line). The rule is conceptually adjacent to `003` (result-handling) and `004` (validation-boundaries), so neighbouring numbering is intentional.

## Related Issues/PRs

- Pairs with miniforge OSS `feat/anomaly-component` (canonical anomaly type)
- Pairs with miniforge OSS `docs/exception-cleanup-inventory` (cleanup backlog)

## Checklist

- [x] New rule file at `foundations/exceptions-as-data.mdc`
- [x] MDC frontmatter (`dewey: "005"`, `alwaysApply: true`)
- [x] Format matches sibling foundation rules
- [x] Anomaly shape, type vocabulary, boundary patterns documented
- [x] Examples (violation + correction + acceptable boundary + programmer-error guard)
- [x] Agent behavior + anti-patterns sections
- [x] Linter design specified (implementation gated on miniforge `ai.miniforge.anomaly`)
- [x] `index.mdc` directory tree updated
- [x] `index.mdc` foundations table updated with the new row
