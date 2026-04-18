# Maintainers

This document lists the people responsible for this repository and what they
own. Maintainers merge PRs, triage issues, shepherd the Dewey catalog, and
cut releases.

## Current maintainers

| Name | Role | Scope | Contact |
|------|------|-------|---------|
| Christopher Lester | Lead maintainer | All categories | [christopher@miniforge.ai](mailto:christopher@miniforge.ai) — GitHub: [@miniforge-ai](https://github.com/miniforge-ai) |

## Becoming a maintainer

The project welcomes category-level maintainers — people who take ownership
of a Dewey range (e.g. `languages/` or `frameworks/`) and review PRs in that
area. If you're interested:

1. Open an issue describing the scope you'd like to cover and your background
   in that area.
2. Land at least two substantive PRs in that scope.
3. A lead maintainer will nominate you; current maintainers will confirm.

Category maintainers are expected to respond to PRs in their area within a
week, or to explicitly hand off when they can't.

## Maintainer responsibilities

- Review PRs for conformance with [`CONTRIBUTING.md`](./CONTRIBUTING.md) and
  the rule format in [`meta/rule-format.mdc`](./meta/rule-format.mdc).
- Triage issues — label, assign Dewey codes to rule proposals, close
  stale/out-of-scope issues with a note.
- Keep [`index.mdc`](./index.mdc) and [`CLAUDE.md`](./CLAUDE.md) in sync with
  the actual rule files.
- Cut releases using [DateVer](./workflows/datever.mdc). Tag releases as
  `v{YYYY.MM.DD.N}` and call out breaking-for-consumers changes in the PR
  description and in `CHANGELOG.md` when it exists.
- Enforce the Code of Conduct. Confidential reports go to
  [christopher@miniforge.ai](mailto:christopher@miniforge.ai).

## Emeritus maintainers

None yet.
