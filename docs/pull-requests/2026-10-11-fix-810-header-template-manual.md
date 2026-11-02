<!--
  Title: Miniforge.ai
  Author: Christopher Lester (christopher@miniforge.ai)
  Copyright 2025-2026 Christopher Lester. Licensed under Apache 2.0.
-->
# fix(810): stop declaring a prepend template the frontmatter format cannot express

## Overview

`project/header-copyright` (dewey 810) declares `remediation.strategy: mechanical`
and `remediation.type: prepend` over a `remediation.template` whose newlines are
written as `\n` escape sequences. The MDC frontmatter grammar performs no escape
processing, so those sequences never become newlines. This PR changes the
declaration to `strategy: manual`, drops `type: prepend`, and documents the
format limit at the rule so the combination cannot be reintroduced by accident.

`autoFixable: false` was already set on `main`, for a separate reason (the
Apache-license scope gate is a human-judgement check). That flag is what
currently keeps the hazard dormant. This PR removes the *latent* half: a rule
still advertising a mechanical prepend whose template is unusable.

## Motivation

The MDC frontmatter value grammar strips surrounding quotes and does nothing
else — see `strip-quotes` in
`components/policy-pack/src/ai/miniforge/policy_pack/mdc_compiler/frontmatter_values.clj`
in the consuming `miniforge` repo. There is no escape processing and no
block-scalar (`|`) syntax, so a multi-line literal cannot be expressed at all.

Verified by compiling a pack with
`ai.miniforge.policy-pack.interface/compile-standards-pack` and reading
`(get-in rule [:rule/remediation :template])` back. The compiled value is:

```
"<!--\\n  Title: Miniforge.ai\\n  Author: Christopher Lester (christopher@miniforge.ai)\\n  Copyright 2025-2026 Christopher Lester. Licensed under Apache 2.0.\\n-->"
```

That is a literal backslash followed by `n`, four times over. No real newline is
present.

### The failure mode this prevents

The prepend step in the consumer is `(str template "\n" content)`
(`apply-prepend` in
`components/compliance-scanner/src/ai/miniforge/compliance_scanner/execute_patch.clj`).
With `autoFixable: true`, a scan of an in-scope Markdown file would therefore
write the whole header as **one line of literal `\n` characters** at the top of
the file.

The second-order effect is the dangerous part. Detection for this rule is
`mode: negative` with both `detection.pattern` (`Christopher Lester`) and
`detection.emailPattern` (`christopher@miniforge\.ai`), matched against the
first ten lines. The broken single line contains **both** strings. So after one
autofix pass the rule reports the file **compliant**.

The result is not a loop that stacks broken headers — it is worse than that. It
is a single silent corruption that satisfies its own gate, with no second run to
reveal it and no warning emitted at compile time.

## Changes in Detail

### `project/header-copyright.mdc`

- `remediation.strategy`: `mechanical` → `manual`.
- `remediation.type: prepend`: removed. Nothing should claim this template is
  machine-applicable.
- `remediation.template`: **removed**. `meta/rule-format` scopes it to
  `remediation.type` `prepend`/`append`, and this rule declares no type. The
  canonical block moves into `## Requirements` as a fenced `html` block, where
  real newlines work and it is actually copyable.
- `remediation.autoFixable: false`: unchanged.
- `<version>`: `1.1.0` → `2.0.0`. The remediation contract changed from
  mechanical to manual, which is a breaking change for any consumer that
  dispatches on `strategy`.
- Body: the existing `autoFixable: false` note is expanded to give both
  independent reasons — the license-scope judgement call, and the format limit —
  and states the precondition for ever making this mechanical again.

No rule was added, renamed, or removed, so `index.mdc`, `agents.md`, and
`CLAUDE.md` need no change. The rule's id, dewey code, globs, and detection
config are all untouched.

## Testing Plan

Compiled the edited file with
`ai.miniforge.policy-pack.interface/compile-standards-pack` and asserted the
resulting rule:

| Field | Before | After |
|---|---|---|
| `:strategy` | `:mechanical` | `:manual` |
| `:type` | `:prepend` | `nil` |
| `:auto-fixable-default` | `false` | `false` |
| `:template` | present | present |
| `:rule/detection` | unchanged | unchanged |

Compile result: `success? true`, `failed-count 0`, `warnings []`.

The consuming `miniforge` side is covered by a companion PR (see below); the
two are independent and neither blocks the other's correctness.

## Deployment Plan

Merge here first, then the consuming `miniforge` repo re-pins its
`standards/miniforge` submodule to the resulting `main` SHA. Pinning a
pre-merge branch commit strands an orphaned pin, so the pin must follow the
merge, not accompany it.

## Related Issues/PRs

- Consuming repo: a `miniforge` PR removes the hardcoded
  `:std/header-copyright` prepend trigger in `execute_patch.clj` and gives
  `strategy: manual` actual force during classification. Today `:manual` is
  accepted by the schema but ignored by the classifier, which only special-cases
  `:semantic` — so `strategy: manual` alone has no effect and the `autoFixable`
  flag is doing all the work.
- `miniforge-ai/thesium-standards#1` applies the same treatment to
  `project/header-proprietary`, which carries the identical defect. Still open.

## Breaking Changes

Consumers dispatching on `remediation.strategy` see `:manual` instead of
`:mechanical`, and `remediation.type` is now absent. Any consumer that treated
the presence of a `template` as a license to patch mechanically must now check
`strategy` as well.

## Review

Copilot raised two findings, both verified and both acted on.

### 1. `remediation.template` has no valid contract without `remediation.type`

Correct, and the better fix. [`meta/rule-format`](../../meta/rule-format.mdc)
defines `remediation.template` as "Full text to insert when `remediation.type`
is `prepend`/`append`". With `type` dropped, retaining the field left a scalar
with no contract — and an escaped one-line scalar is not safely copyable as a
multi-line block anyway, which was the whole complaint.

So `remediation.template` is **removed** rather than retained as copy-text, and
the canonical block now lives in `## Requirements` as a fenced `html` block,
where real newlines work. Verified: the compiled rule's `:template` is `nil`,
and the block appears in `:rule/knowledge-content` with genuine newlines.

This also fixed a latent inconsistency — the `<example>` block showed a
`Title / Subtitle / Author / Line / Copyright` shape that did not match the
template it was supposedly illustrating. It now shows the actual header.

### 2. This PR doc was itself in violation of rule 810

Correct. The doc is matched by the `docs/**/*.md` glob and omitted the header,
and the 29 existing headerless PR records do not excuse a new one. The header
is now present on this file.

That leaves those 29 as a real pre-existing violation of this repo's own rule.
Fixing them is a separate sweep, not this stratum — flagged for follow-up. The
alternative, narrowing the globs to exclude `docs/pull-requests/`, was
considered and rejected: the frontmatter has no glob-exclude mechanism, so it
would mean enumerating included subdirectories, and enumerated directory globs
are exactly what caused `specs/**/*.md` to match zero files in another repo.

## Checklist

- [x] Rule compiles with no warnings and no failures
- [x] Compiled remediation config asserted field by field
- [x] `remediation.template` removed; canonical block moved to the rule body
- [x] This PR doc carries the 810 header
- [x] Rule version bumped to reflect a breaking contract change
- [x] Format limit documented at the rule, with the precondition for reversing it
- [x] Template retained as the canonical hand-applied block
- [x] No index / agents.md / CLAUDE.md change needed (no rule added or removed)
- [ ] Consuming `miniforge` submodule re-pinned after merge
