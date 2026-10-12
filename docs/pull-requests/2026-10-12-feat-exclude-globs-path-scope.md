<!--
  Title: Miniforge.ai
  Author: Christopher Lester (christopher@miniforge.ai)
  Copyright 2025-2026 Christopher Lester. Licensed under Apache 2.0.
-->
# feat(rule-format): add `excludeGlobs` so a scope carve-out can be enforced

## Overview

A rule's scope today is expressible only as an inclusion list (`globs`). A rule
whose scope is correctly broad but has one subtree it must not touch has no way
to say so in frontmatter, so the carve-out is written in prose — where no runner
reads it. This PR adds `excludeGlobs` to the frontmatter grammar and applies it
to its first consumer, `workflows/bb-over-shell` (dewey 740).

## Motivation

`workflows/bb-over-shell` was inert until recently: its `detection:` /
`enforcement:` block was written in nested-block YAML style, which the MDC
frontmatter grammar does not parse, so it compiled to `{:type :custom}` and
`{:action :audit}`. On `main` it is dotted style and live — `hard-halt`,
`:high`, with a real `:content-scan` pattern.

Live, it matches three files in the consuming `miniforge` repo:

```text
examples/demo-polyglot-repo/run-demo.sh
examples/demo-polyglot-repo/scripts/setup.sh
examples/demo/run-demo.sh
```

All three are under `examples/`, which the rule's own body already exempts:

> A demo polyglot repo that contains `.sh` and `.py` is the *subject* of
> analysis, not automation […] Scripts there exist to be analysed and MUST NOT
> be migrated.

That exemption is prose. The compiler reads frontmatter, so a `hard-halt` gate
fires on exactly the files the rule says to leave alone. The rule is correct and
its scope declaration is incomplete.

### Why not narrow `globs` instead

`globs` patterns are anchored `PathMatcher` patterns. Narrowing to exclude
`examples/` means replacing `**/*.sh` with an enumeration of the directories
that *are* in scope. An enumeration of inclusions goes stale every time a
directory is added, and it fails silently — the rule simply stops matching new
paths, with nothing to indicate it. (`specs/**/*.md` in `thesium-standards`
matched zero files for exactly this reason.) An exclusion names the one subtree
that is special and leaves the inclusion broad, so new directories stay covered
by default.

### Why not `remediation.excludePathContains`

That field already exists and does not do this. In the consumer it is read in
`classify` and gates `:auto-fixable?` only — it never suppresses detection and
never suppresses a gate. A `hard-halt` rule with `excludePathContains` still
blocks; it just blocks without offering a fix. `bb-over-shell` has no
remediation block at all.

## Changes in Detail

### `meta/rule-format.mdc`

- `excludeGlobs` documented in its own **Scope exclusion** subsection, placed
  directly after **Activation**. Scope exclusion belongs beside `globs` rather
  than in the automated-fix table — it decides whether the rule applies at all
  — but it is not itself an activation source, and putting it inside a table
  headed "include at least one" would read as though it were. The subsection
  opens by saying so: a rule carrying only `excludeGlobs` is still never
  injected.
- Semantics stated: wherever a path is tested — scan, gate, per-file rule
  selection — a match puts the file out of scope and beats any `globs` match,
  including for an `alwaysApply: true` rule. Whole-prompt injection has no path
  to test, so it is explicitly out of the field's reach rather than left
  ambiguous; stopping injection is `alwaysApply`'s job.
- The `## Core Structure` template gains the field as a commented option, so
  authors copying the template see it — under its own comment separating it
  from the two activation fields, since the template's `# Activation: include
  at least one` line would otherwise appear to cover three fields.
- A short authoring note: a carve-out stated only in prose is not enforced, and
  one exclusion is preferable to an enumeration of inclusions.

### `workflows/bb-over-shell.mdc`

- `excludeGlobs: ["examples/**"]` added. `globs` is unchanged.
- The `## Context` carve-out bullet now names the field that carries it, and
  states the consequence of the mechanism: an exclusion cannot be overridden
  per file, so a real build script must move out of `examples/` rather than the
  rule widening to accommodate it.
- A second bullet covers fixture trees outside `examples/`: add the root when
  one appears, do not pre-enumerate directories that do not exist. `examples/**`
  is the only fixture root with matching files today.
- `<version>`: `2.0.0` → `2.1.0`. Scope narrowed; no requirement changed.

No rule was added, renamed, or removed, so `index.mdc`, `agents.md`, and
`CLAUDE.md` need no change.

## Testing Plan

Compiled this tree with `ai.miniforge.policy-pack.interface/compile-standards-pack`
from the consuming `miniforge` repo, at its **current** submodule pin (the
compiler that does not yet know the field):

| Assertion | Result |
| --- | --- |
| `success?` | `true` |
| `compiled-count` / `failed-count` | `59` / `0` |
| `warnings` | `[]` |
| `:std/bb-over-shell` `:rule/applies-to` | unchanged; no `:exclude-globs` |
| Files firing `bb-over-shell` over the `miniforge` tree | 3 — the three above |

An unknown frontmatter key is dropped without a warning. That is the expected
result here and it is also the deployment constraint: until the consumer
understands the field, the exclusion has no effect. See **Deployment Plan**.

Not yet verified against a compiler that understands the field — that compiler
does not exist until step 2 of the deployment plan. This doc is a living record
and will be updated with the result, asserting `:exclude-globs ["examples/**"]`
on `:rule/applies-to` and 0 files firing over the `miniforge` tree.

## Deployment Plan

Three steps, in this order. Steps 1 and 2 are independent of each other; step 3
requires both.

1. **Merge here.** The field is inert at the current pin — an unknown key, no
   behaviour change, no warning. Safe to merge ahead of the consumer.
2. **Consumer support.** A `miniforge` PR teaches the compiler to read
   `excludeGlobs` into `:rule/applies-to :exclude-globs`, and makes every site
   that computes a rule's path scope honour it (`applicability`,
   `registry.support`, the compliance scanner's `file-pred`, the semantic
   analyzer's file selection).
3. **Re-pin.** `miniforge` moves `standards/miniforge` to this repo's `main`
   SHA **after** both have merged. Pinning before step 2 arms the `hard-halt`
   gate on the three `examples/` files with no exclusion in effect.

Pinning a pre-merge branch commit strands an orphaned pin when the PR is
squashed, so the pin follows the merge rather than accompanying it.

## Consumer coverage

The consuming PR (miniforge-ai/miniforge#2025) honours `excludeGlobs` in the
workflow gate's rule applicability and in the compliance scanner's file
selection. It does **not** yet cover `semantic-analyzer`'s three
file-selection sites, which are blocked behind a pre-existing rule 210 layer
violation in that namespace; the follow-up is named in that PR.

So the field's semantics as specified above are the contract, and one
consumer path is not there yet. The gap is latent: `bb-over-shell` is the
only rule using the field and it is `:content-scan`, so it never reaches the
semantic judge.

## Related Issues/PRs

- miniforge-ai/miniforge-standards#129 — `project/header-copyright` (dewey 810)
  remediation contract. Independent: no file overlap, and neither blocks the
  other. Its review raised the same class of question for 810's `docs/**/*.md`
  glob and resolved it by adding the header rather than by narrowing scope;
  `excludeGlobs` does not change that decision and is not proposed for 810 here
  (in `miniforge`, 901 of 902 glob-matched files carry the header, so a
  `docs/pull-requests/**` exclusion would be wrong for the main consumer).
- miniforge-ai/miniforge-standards#119 — also edits 810's `remediation.template`,
  which #129 removes. Will conflict with #129; flagged, not addressed here.

## Breaking Changes

None. `excludeGlobs` is optional and additive. A consumer that does not read it
behaves exactly as before. Narrowing `bb-over-shell`'s scope can only reduce the
violations a consumer reports.

## Checklist

- [x] `excludeGlobs` documented in the Activation table, with evaluation order
- [x] Field added to the `## Core Structure` template
- [x] First consumer (`bb-over-shell`) migrated, `globs` left untouched
- [x] Rule body states which field carries the carve-out
- [x] `<version>` bumped on the changed rule
- [x] Compiles clean at the current consumer pin, with the field ignored
- [x] No index / `agents.md` / `CLAUDE.md` change needed (no rule added or removed)
- [ ] Re-verified against the consumer's new compiler before the pin moves
- [ ] Consuming `miniforge` submodule re-pinned after merge (step 3)
