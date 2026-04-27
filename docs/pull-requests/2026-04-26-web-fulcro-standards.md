# feat(web/fulcro): web frontend standards + Fulcro rulepack + API surface classes

## Overview

Adds the standards substrate Fleet's Fulcro conversion (next PR) will
build against. Three rule families:

1. **Web foundation** — JavaScript / CSS / HTML language rules + a
   `web-architecture-mode` router rule that decides whether a surface
   is server-rendered HTML, vanilla JS, or Fulcro.
2. **Fulcro rulepack** — `frameworks/fulcro.mdc` (FUL-001–035) extracted
   from the *Fulcro Developer's Guide*, with `frameworks/fulcro-rad.mdc`
   (RAD-001–005) for the optional RAD layer.
3. **API surface classes** — `frameworks/api-surface-classes.mdc`
   formalises the three-class taxonomy: internal (EDN/Transit) vs
   first-party cross-language (JSON/SSE) vs customer extension
   (REST/webhooks). The architectural rule: canonical domain model and
   external wire formats are not the same thing. EDN is the semantic
   centre.

Source: the user's `fleet-web-frontend-and-fulcro-standards.md` draft,
adapted to this repo's `.mdc` rule format and Dewey numbering.

## What landed

### `languages/`

| Dewey | File | Rule IDs |
|---|---|---|
| 250 | `javascript.mdc` | JS-001–008 |
| 260 | `css.mdc` | CSS-001–008 |
| 270 | `html.mdc` | HTML-001–004 |

### `frameworks/`

| Dewey | File | Rule IDs |
|---|---|---|
| 330 | `web-architecture-mode.mdc` | WEB-001–004 + frontend review checklist |
| 331 | `browser-security.mdc` | BSEC-001–004 |
| 332 | `api-surface-classes.mdc` | API-001–006 |
| 340 | `fulcro.mdc` | FUL-001–035 |
| 341 | `fulcro-rad.mdc` | RAD-001–005 |

### Index updates

- `index.mdc` — registers all 8 new rules in the catalog tables and the
  directory tree.
- `CLAUDE.md` — quick-reference table updated; Dewey-range key updated.

## Why these rules, why now

Pulled from a strategic decision recorded in the user's draft + a
back-and-forth on Fulcro vs alternatives. Two threads converged:

1. **Frontend choice for Fleet.** Fleet's Phase A/B/C dashboard
   already exceeds the migration-trigger bar (`web-architecture-mode`
   §migration rule): durable client state, multi-entity edits via the
   control proxy, optimistic updates needed for Apply buttons, async
   job state in the audit log. Fulcro is the fit. The Fulcro-Book-as-
   canonical-source argument is what makes it work for an
   agent-edited codebase: standards become extraction work, not
   opinion work.
2. **Multi-surface architecture.** Miniforge already speaks Transit
   internally; native Mac/Windows apps and customer extension points
   need plain JSON/SSE. The three-API-classes rule captures this
   without forcing a single wire protocol on every consumer.

## Standards conformance

- All new rules use the existing `.mdc` format with frontmatter
  (`dewey`, `description`, `globs`).
- Rule IDs (JS-001, CSS-001, FUL-001, etc.) preserved verbatim from
  the source draft for traceability — when a PR review cites
  "violates FUL-007", the ID is stable across rulepack revisions.
- Cross-references between rules use the `category/file.mdc RULE-ID`
  shorthand (e.g. `frameworks/fulcro.mdc FUL-023`).
- Severity guidance included in `fulcro.mdc` for policy compilation
  (blocker / major / minor / advisory) per the source draft's §11.

## Out of scope (deferred to follow-on PRs)

- **Fleet ADR** recording the Fulcro decision, naming the bus-factor
  risk explicitly, and binding the conversion to this rulepack as a
  precondition. Lands in `miniforge-fleet`, not here.
- **Fleet domain seed** (entity namespaces from §9 of the source
  draft) — Fleet-specific, lands in `miniforge-fleet`'s `domain.cljc`.
- **Fulcro skeleton conversion** of the Phase A/B/C dashboard — the
  third PR in the stack, depends on the ADR which depends on this
  rulepack.

## Try it

```bash
# Browse the new rules
ls miniforge-standards/frameworks/{web-architecture-mode,browser-security,api-surface-classes,fulcro,fulcro-rad}.mdc
ls miniforge-standards/languages/{javascript,css,html}.mdc

# Confirm index is in sync
grep -c "dewey" miniforge-standards/index.mdc
```

## Related

- Branch: `chris/web-fulcro-standards`
- Source draft: `fleet-web-frontend-and-fulcro-standards.md` (user-supplied)
- Followed by: `miniforge-fleet` ADR PR (Fulcro decision + domain seed),
  then `miniforge-fleet` Fulcro skeleton PR (dashboard conversion).
