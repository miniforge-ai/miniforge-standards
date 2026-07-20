# feat: reference stratum-lint enforcement in rule 210

## Overview

Adds an enforcement bullet to `languages/clojure.mdc`'s per-file stratified
design section, pointing at the Clojure implementation of
[stratum-lint](https://github.com/miniforge-ai/stratum-lint).

## Motivation

Rule 221 (Python) references its linter; rule 210's Layer-heading convention
was un-checked prose. The linter now exists (bb-native, headings as source
of truth, optional `^{:stratum n}` metadata, `--fix` inference/rewrite), so
the rule should say so.

## Changes in Detail

- `languages/clojure.mdc`: one bullet in "Per-file stratified design" —
  check codes (SL001-SL006), CI invocation, metadata semantics, `--fix`
  behavior, headingless-file exemption.

## Testing Plan

Docs-only. Verified the referenced CLI invocation and codes against the
shipped tool (stratum-lint PR #1, CI green including self-lint dogfood).

## Deployment Plan

Merges to `main`; consuming repos adopt the CI step with their next
`.standards` bump.

## Related Issues/PRs

- miniforge-ai/stratum-lint#1 (Clojure linter)
- miniforge-standards#82 (rule 221, Python counterpart)

## Checklist

- [x] Codes and invocation match the shipped tool
- [x] PR doc committed with the change
