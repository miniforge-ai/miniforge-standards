# feat(web/fulcro): web frontend standards + Fulcro rulepack + API surface classes

## Overview

Adds the standards substrate that any miniforge web product can build
against. Three rule families:

1. **Web foundation** — JavaScript / CSS / HTML language rules + a
   `web-architecture-mode` router rule that decides whether a surface
   is server-rendered HTML, vanilla JS, or Fulcro.
2. **Fulcro rulepack** — `frameworks/fulcro.mdc` (FUL-001–035) extracted
   from the *Fulcro Developer's Guide*, with `frameworks/fulcro-rad.mdc`
   (RAD-001–005) for the optional RAD layer.
3. **API surface classes** — `frameworks/api-surface-classes.mdc`
   formalises the three-class taxonomy: internal (EDN/Transit) vs
   first-party cross-language (JSON/SSE) vs customer extension
   (REST/webhooks).

## Motivation

Two pressures converged:

1. **Agent-edited browser code is failing for the same reason
   everywhere.** Whether the framework is React, Vue, or hand-rolled
   JS, agents trained on the median public corpus produce code the
   maintainer rejects. The variable that determines whether a
   framework is usable in this environment is **the existence of a
   single canonical reference text** that standards can be extracted
   from. Fulcro has the *Developer's Guide*; React has a fractal of
   competing opinions. This rulepack converts the Fulcro Book into
   stable rule IDs (FUL-001–035) so PR review can cite a rule rather
   than re-litigate framework folklore.
2. **Wire format ≠ canonical model.** Miniforge already speaks Transit
   internally. Native apps and customer extension points need plain
   JSON / SSE. The three-API-classes rule captures this without
   forcing a single wire protocol on every consumer.

Without this PR, the next product that wants stateful UI either
re-derives the standards locally (drift) or ships without them
(churn-by-review). With this PR, it consumes the rulepack via the
existing `.standards/` submodule pattern.

## Changes in Detail

### `languages/`

| Dewey | File | Rule IDs | Globs |
|---|---|---|---|
| 250 | `javascript.mdc` | JS-001–008 | `**/*.{js,mjs}` |
| 260 | `css.mdc` | CSS-001–008 | `**/*.{css,scss}` |
| 270 | `html.mdc` | HTML-001–004 | `**/*.{html,htm}` |

### `frameworks/`

| Dewey | File | Rule IDs | Globs |
|---|---|---|---|
| 330 | `web-architecture-mode.mdc` | WEB-001–004 + frontend review checklist | web file types |
| 331 | `browser-security.mdc` | BSEC-001–004 | browser file types |
| 332 | `api-surface-classes.mdc` | API-001–006 | service file types |
| 340 | `fulcro.mdc` | FUL-001–035 | `**/*.{cljs,cljc}` |
| 341 | `fulcro-rad.mdc` | RAD-001–005 | `**/*.{cljs,cljc}` |

### Catalog updates

- `index.mdc` — registers all 8 new rules in catalog tables and the
  directory tree. Also corrects pre-existing `polylith.mdc` Dewey
  drift (`300` → `310`) so the catalog matches the rule file's own
  frontmatter.
- `CLAUDE.md` — quick-reference table extended with the 8 new rules;
  Dewey-range key extended; `polylith.mdc` Dewey drift corrected.

### Cross-references

- `web-architecture-mode` references `fulcro.mdc`, `fulcro-rad.mdc`,
  `javascript.mdc`, `css.mdc`, `html.mdc`, `browser-security.mdc`,
  `api-surface-classes.mdc` from its router logic.
- `fulcro.mdc` references `web-architecture-mode.mdc` (when Fulcro
  applies), `api-surface-classes.mdc` (Pathom is class 1),
  `browser-security.mdc` (BSEC-004 for destructive mutations),
  `javascript.mdc` (JS-007 for confirmation guards).
- `browser-security.mdc` references `javascript.mdc` JS-007 and
  `fulcro.mdc` FUL-023 from BSEC-004.
- `api-surface-classes.mdc` is referenced from `fulcro.mdc` FUL-028
  and from `web-architecture-mode.mdc` WEB-004.

### Naming and ID stability

Rule IDs (JS-001, CSS-001, FUL-001, etc.) preserved verbatim from the
source draft so PR-review citations remain stable across rulepack
revisions. Severity guidance (blocker / major / minor / advisory)
included in `fulcro.mdc` for downstream policy compilation.

## Testing Plan

This is a documentation-only PR; the rules themselves are not
executable. Validation is structural:

- `grep -c "dewey" miniforge-standards/index.mdc` — confirms catalog
  entry count matches file count.
- For each new rule file, head -5 contains a `dewey:` frontmatter line
  — confirms every rule declares its Dewey code.
- Manual cross-reference walk — every `frameworks/X.mdc RULE-ID`
  reference in the new files resolves to an existing rule.
- Manual rule-format check against `meta/rule-format.mdc`.

The downstream consumers (next two PRs in the stack — the Fleet ADR
and the Fleet Fulcro skeleton conversion) will be the first runtime
exercise of this rulepack; their PR reviews will surface any rule
that's ambiguous, unactionable, or missing.

## Deployment Plan

The standards repo is consumed as a `.standards/` git submodule by
downstream repos. Deployment is:

1. Merge to `main` in `miniforge-standards`.
2. Each consuming repo updates its submodule pointer
   (`git submodule update --remote .standards`) when the rulepack
   revision is needed by an in-flight PR.
3. No coordinated rollout required — rules are advisory until a
   consuming repo configures policy compilation against them.

No breaking changes; the only catalog edit is correcting the
pre-existing `polylith.mdc` Dewey drift, which is a fix not a break
(the rule file's own frontmatter was already `"310"`).

## Related Issues/PRs

- **Branch**: `chris/web-fulcro-standards`
- **Source draft**: user-supplied
  `fleet-web-frontend-and-fulcro-standards.md`, adapted to the repo's
  `.mdc` format and Dewey numbering.
- **Followed by**:
  1. `miniforge-fleet` ADR — records the Fulcro decision, names
     bus-factor risk, binds the conversion to this rulepack as a
     precondition, includes the consuming product's domain seed.
  2. `miniforge-fleet` Fulcro skeleton — Phase A/B/C dashboard
     conversion. Existing class-2 JSON endpoints (`/dag`,
     `/recommendations`, `/audit`, `/control/...`) stay alive
     alongside the new internal Pathom / Transit surface.

## Checklist

- [x] All 8 new rules declared with `dewey`, `description`, `globs`
      frontmatter
- [x] Rule IDs preserved verbatim from source draft (JS-001..008,
      CSS-001..008, HTML-001..004, WEB-001..004, BSEC-001..004,
      API-001..006, FUL-001..035, RAD-001..005)
- [x] `index.mdc` catalog entries added for all 8 rules
- [x] `CLAUDE.md` quick-reference table extended for all 8 rules
- [x] `CLAUDE.md` Dewey-range key extended for all 8 rules
- [x] Pre-existing `polylith.mdc` Dewey drift (`300` → `310`)
      corrected in both `index.mdc` and `CLAUDE.md`
- [x] Cross-references between new rules use the
      `frameworks/X.mdc RULE-ID` shorthand
- [x] Severity guidance included in `fulcro.mdc` for policy
      compilation
- [x] Standards files are repo-generic (no product-specific framing
      like Fleet-only assumptions)
- [x] PR doc filename matches branch name per
      `workflows/pr-documentation.mdc`
