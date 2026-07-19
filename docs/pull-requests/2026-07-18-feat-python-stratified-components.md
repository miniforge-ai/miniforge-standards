# feat: Python stratified-components rule (dewey 221)

## Overview

Adds `languages/python-stratified-components.mdc` (dewey 221) — the Python
mirror of the Clojure component model already codified in `languages/clojure`
(210) and `frameworks/polylith` (310). With this rule, the standards carry a
worked stratified-design component pattern in both Clojure and Python.

## Motivation

`languages/python` (220) prescribes functional Python and references
stratified design, but gives no concrete component shape. Clojure has one
(per-file Layer headings, interface namespaces, ≤3 layers, splitting
strategy); Python had nothing equivalent, so agent-written Python had no
target structure. The pattern was validated on a real component (an in-memory
filesystem exercise) before being codified here.

## Changes in Detail

- **New rule** `languages/python-stratified-components.mdc` (dewey 221):
  - Component anatomy: package = component; `interface.py` is the only
    sanctioned import surface; one implementation module plus flat leaf
    modules (`model.py`, `exceptions.py`).
  - Extraction heuristic: flat leaves are fine; a DAG *among implementation
    modules* inside a component signals a missing component boundary —
    extract instead of nesting.
  - Interface: explicit-state functions are the canonical contract; a class
    facade owning the state is optional consumer convenience.
  - Explicit state: one state dataclass, passed as first argument;
    `default_factory` lambda rule for mutable/composite defaults;
    `defaultdict[...]` annotations.
  - Strata: `@stratum(n)` decorator, monotonic ascending file order, calls
    only to strata ≤ n, ≤3 strata per implementation module (mirrors the
    Clojure 3-layer maximum).
  - Absence/errors: one domain exception per component; uniform miss
    semantics per stratum; empty collections over `None` when absence means
    "nothing to process"; raise or result value when absence is
    domain-intrinsic.
  - Enforcement: strict type checking (explicit-state style makes dropped
    state arguments a static error), `import-linter` forbidden + layers
    contracts, and stratum discipline via
    [stratum-lint](https://github.com/miniforge-ai/stratum-lint) (SL001-SL005),
    which also ships the `@stratum` decorator.
- **`languages/python.mdc`**: pointer to the new rule under Project structure.
- **`index.mdc`**: tree + languages table entries for 221.
- **`CLAUDE.md`**: quick-reference row, rules catalog entry, Dewey range entry.

## Testing Plan

Documentation-only change; no code paths. Validation: frontmatter matches
`meta/rule-format` schema (dewey + description + globs), index/CLAUDE.md/rule
mutually consistent, examples are syntactically valid Python.

## Deployment Plan

Merges to `main`; consuming repos pick it up on their next `.standards`
submodule bump.

## Related Issues/PRs

- [stratum-lint](https://github.com/miniforge-ai/stratum-lint) — the linter
  and decorator referenced in the rule's Strata and Enforcement sections
  (seeded 2026-07-19; private until OSS review completes).

## Checklist

- [x] Rule follows `meta/rule-format` core structure
- [x] `index.mdc` updated (tree + table)
- [x] `CLAUDE.md` updated (quick reference + catalog + Dewey ranges)
- [x] Cross-reference added in `languages/python.mdc`
- [x] Good and invalid examples included
