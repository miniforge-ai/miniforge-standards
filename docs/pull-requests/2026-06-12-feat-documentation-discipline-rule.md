# feat: add documentation-discipline rule (dewey 600)

## Overview

Adds `documentation/documentation-discipline.mdc` (dewey `600`, `alwaysApply: true`) —
the first rule in the Documentation band (600-699) — and wires it into the `index.mdc`
and `CLAUDE.md` catalogs. The rule makes documentation high-fidelity by making it
scarce: source files carry the **current contract only**, with per-surface length
limits, and history/rationale/narrative move out to external docs.

## Motivation

Source files have been accreting documentation overload — long namespace essays,
per-function docstrings that narrate the implementation, schema comments that mix the
current contract with design history ("originally…", "later phase…"). The goal is a
much higher fidelity bar: far less documentation, and what remains is contract, not
story.

`foundations/self-documenting-code` (009) already governs comment *quality* — comment
WHY, never WHAT; document the boundary, keep the impl light. It does not set how much
documentation each surface may carry, how schemas and fields should be documented, or
where narrative belongs. This rule fills that gap.

## The split (why a new rule, not an edit to 009)

Two distinct concerns:

- **009 self-documenting-code** — *whether* a comment earns its place (WHY vs WHAT;
  boundary-vs-impl docstrings). Unchanged.
- **600 documentation-discipline** — *how much* documentation, and *where each kind
  lives* (length limits; schemas/fields as contracts; narrative → ADR/PR doc; examples
  → tests).

They compose: 600 defers to 009 on comment quality; 009's Quick-Reference row, which
over-claimed "where docs live," now points the placement/length question at 600.

## Changes in Detail

- `documentation/documentation-discipline.mdc` — 88 lines:
  - Lead principle: source describes the current contract only; default to less.
  - **Length-limit table** (ns 1–5 lines, schema 1–3 sentences, field one sentence,
    ordinary `defn` docstring omitted by default, inline comment one line).
  - **Schemas as contracts** — what a schema docstring states vs omits; field meaning
    goes in the malli `:description`, not a comment; obvious fields get nothing.
  - **Placement table** — rationale → ADR, architecture → docs, history → PR doc (721)
    / changelog, examples → tests, "how you arrived at the code" → PR description.
  - Executable-examples preference; reject-in-review list; "touching over-documented
    code" guidance (preserve headers (810) + named-constant docstrings (006)).
- `index.mdc` — directory tree + a new `### documentation/` table row.
- `CLAUDE.md` — Rules Catalog tree, Quick-Reference (split the 009 row), Dewey Ranges
  600 entry.

## Testing Plan

Documentation-only; validation is structural (no `bb test` in this repo — the pack is
consumed as a `.standards/` submodule):

- Frontmatter + `# Title (ALWAYS)` + bold-lead + section style match sibling
  foundations rules (`named-constants`, `self-documenting-code`).
- Dewey `600` is unique; referenced consistently across the rule file, the `index.mdc`
  tree and table, and the `CLAUDE.md` tree, Quick-Reference, and Dewey Ranges.
- The rule models its own discipline — 88 lines, no history, contract-only.

## Deployment Plan

Additive. Downstream repos re-pulling the standards submodule pick it up; no migration.
Existing over-documented code is addressed opportunistically under the "when touching"
clause, not in a sweep.

## Related Issues/PRs

- Complements `foundations/self-documenting-code` (009); deferred enforcement
  (detection block / `code-review-rigor` 720 obligation) is a separate decision —
  miniforge already compiles these rules to policy and gates on them in review.

## Checklist

- [x] New rule at `documentation/documentation-discipline.mdc` (`dewey: "600"`, `alwaysApply: true`)
- [x] Length limits, schema/field contract guidance, placement table, executable-examples
- [x] Deduped against 009; cross-references 009 / 006 / 810 / 721
- [x] `index.mdc` tree + table updated; `CLAUDE.md` tree + Quick-Reference + Dewey Ranges updated
- [x] Dewey 600 unique; catalogs consistent
