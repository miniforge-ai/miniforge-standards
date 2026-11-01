# feat: Add the PR merge gate rule (dewey 723)

## Overview

New always-apply rule `workflows/pr-merge-gate` (dewey 723): a PR merges only
when four gates pass on its current head — adversarial review (dewey 720),
rules review against these standards, green CI, and a resolved Copilot review.
PRs open ready for review, never as drafts, and the owning session merges once
the gates pass instead of waiting for the operator.

## Motivation

The operator gave this instruction to agent sessions more than once, and
sessions kept missing it: on 2026-11-01 two miniforge PRs (#2296, #2305)
merged on green CI and a clean Copilot review alone, and their post-merge
reviews found a high-severity bug (governed releases scanned a directory that
does not exist inside the capsule) and a must-fix rule violation. The rule lived only in
conversation, and then only in miniforge's `agents.md`; sessions in other
repositories that consume these standards never saw it.

## Changes in Detail

1. `workflows/pr-merge-gate.mdc` (new, dewey 723, `alwaysApply: true`): the
   four gates; timing (gates 1 and 2 before opening and before every push;
   re-request Copilot after a push; a base-branch merge is a new head); what to
   do when a gate cannot pass (stop and ask; no Copilot review; red base
   branch); Copilot findings that conflict with these standards (answer with
   the trace and the rule, resolve, do not change the code); delegation; scope
   for trivial and documentation-only PRs.
2. `index.mdc`, `agents.md`, `CLAUDE.md`: the rule in the file tree, the
   catalog table, the Dewey list and the "Merge a PR" quick reference.

## Testing Plan

Documentation only. Every cross-reference in the rule was checked against the
repository: `workflows/code-review-rigor` (720), `foundations/no-dead-code`,
`index.mdc`, and the 720 obligations it defers to (stop and ask when the review
cannot be run; trivial diffs exempt).

## Deployment Plan

Consuming repositories pick the rule up when they bump their `standards`
submodule; `notify-consumers` dispatches that. Miniforge's own `agents.md`
already carries the same gate (miniforge #2315) and can point here after the
bump.

## Related Issues/PRs

- miniforge-ai/miniforge#2315 (the same gate in miniforge's `agents.md`)
- miniforge-ai/miniforge#2296, #2305 (merged without gates 1 and 2)
