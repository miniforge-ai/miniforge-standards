# chore: open-source readiness for miniforge-standards

## Overview

Adds the documentation and governance files required to make
`miniforge-standards` a usable open-source repository: public-facing README,
Apache-2.0 licensing, contributor and security policies, maintainer listing,
CODEOWNERS, and GitHub issue/PR templates. No rule content changes — the
existing `.mdc` catalog, `CLAUDE.md`, `agents.md`, and `index.mdc` are
untouched.

## Motivation

This repository is the shared source of truth for engineering rules across
Miniforge.ai repos and is pulled in as a `.standards/` submodule. Making it
public means that anyone forking [Miniforge][mf] or building in the
ecosystem can consume the same baseline rules without re-deriving them.
Before publishing, the repo needs the standard OSS furniture: a license so
consumers know their rights, a README so readers can orient, a
CONTRIBUTING guide so proposals come in the right shape, a MAINTAINERS file
so ownership is legible, a CODE_OF_CONDUCT so community expectations are
set, a SECURITY policy so vulnerability reports have a channel, and a
CODEOWNERS + templates pair so GitHub routes work sensibly on day one.

[mf]: https://github.com/miniforge-ai/miniforge

## Changes in Detail

### New top-level files

- `README.md` — public-facing overview. Explains what the repo is, the Dewey
  classification scheme, how to install as a submodule, and how versioning
  works. Links into the existing catalog (`index.mdc`, `CLAUDE.md`,
  `agents.md`, `meta/rule-format.mdc`).
- `LICENSE` — Apache License 2.0, full canonical text. Copyright
  `2025-2026 Christopher Lester (christopher@miniforge.ai)` per
  `project/header-copyright` (dewey 810).
- `NOTICE` — Apache-2.0 NOTICE file with attribution.
- `CONTRIBUTING.md` — issue-first workflow for rule proposals, Dewey
  placement guidance, link to `meta/rule-format`, reminder of
  `workflows/pr-layering` and `workflows/pre-commit-discipline`, DCO-style
  sign-off note (no separate CLA).
- `MAINTAINERS.md` — Christopher Lester listed as lead maintainer. Describes
  a lightweight path for category-level maintainers (two substantive PRs +
  nomination).
- `CODE_OF_CONDUCT.md` — Contributor Covenant 2.1 incorporated by reference
  to the canonical URL, with a short in-repo summary of pledge, standards,
  enforcement contact, and scope. The canonical full text is linked rather
  than inlined.
- `SECURITY.md` — scope (what counts as a security issue for a docs repo),
  reporting channel (private email to `christopher@miniforge.ai`), 90-day
  coordinated-disclosure window.

### New `.github/` files

- `.github/CODEOWNERS` — default owner `* @H31MDALLR`.
- `.github/PULL_REQUEST_TEMPLATE.md` — enforces the "what failure does this
  prevent + how do you verify it" bar from `CONTRIBUTING.md`; checklist
  includes index/CLAUDE sync, positive+negative examples, PR size cap, and
  a no-`--no-verify` reminder.
- `.github/ISSUE_TEMPLATE/rule-proposal.md` — structured template for new
  rules: slug, proposed Dewey code, problem, requirements,
  positive/negative examples, scope, always-apply justification.
- `.github/ISSUE_TEMPLATE/bug-report.md` — simple template for catalog
  errors, broken links, and frontmatter issues.

### Modified files

- `.gitignore` — replaced the single-entry `.cursor.zip.rename-me.zip` line
  with a proper ignore list: `.DS_Store`, IDE dirs (`.idea/`, `.vscode/`),
  editor swap files (`*.swp`, `*.swo`, `*~`), local env (`.env`,
  `.env.local`), and `*.zip`.

### Deleted files

- `.cursor.zip.rename-me.zip` — stale archive (likely the original rule
  dump). Confirmed not needed by the repo owner.
- `.DS_Store` — untracked macOS metadata.

### Not touched (explicitly out of scope)

- `CLAUDE.md`, `agents.md`, `index.mdc`, and every rule `.mdc` file. This
  PR is governance-only; no rule text changes so downstream repos will
  observe no behavioral drift from pinning past this commit.

## Testing Plan

- Visual review of each new Markdown file for rendering correctness.
- Verify intra-repo links resolve:
  - `README.md` → `index.mdc`, `CLAUDE.md`, `agents.md`,
    `meta/rule-format.mdc`, `workflows/datever.mdc`, `CONTRIBUTING.md`,
    `CODE_OF_CONDUCT.md`, `MAINTAINERS.md`, `LICENSE`, `NOTICE`,
    `CHANGELOG.md` (described as "when present").
  - `CONTRIBUTING.md` → `meta/rule-format.mdc`, `workflows/pr-layering.mdc`,
    `workflows/pr-documentation.mdc`,
    `workflows/pre-commit-discipline.mdc`, `index.mdc`, `CLAUDE.md`,
    `LICENSE`, `CODE_OF_CONDUCT.md`, `MAINTAINERS.md`.
  - `SECURITY.md` → `workflows/datever.mdc`.
- Render the rule-proposal and bug-report issue templates in the GitHub UI
  after push to confirm frontmatter is valid.
- Confirm `.gitignore` is hiding what it should locally (`.DS_Store` in
  particular, given the macOS host).

## Deployment Plan

This is a documentation-only PR. Merging to `main` immediately makes the
repo ready for "Make public" in GitHub Settings. Recommended sequence:

1. Merge this PR.
2. Tag the merge commit `v2026.04.17.1` per `workflows/datever`.
3. Flip repository visibility to Public in Settings.
4. Enable GitHub features for public repos: Discussions (optional),
   Dependency Graph, and Private Vulnerability Reporting (optional; not
   required since `SECURITY.md` already defines a private channel).

No consumer action is required. Existing submodule pins continue to work.

## Related Issues/PRs

- None. This PR stands alone.

## Rollback Plan

Pure docs/governance. Revert the merge commit to remove all additions; the
repo returns to its pre-PR state. No data or build artifacts are affected.

## Checklist

- [x] README drafted for public audience
- [x] LICENSE (Apache-2.0) added with correct copyright holder
- [x] NOTICE added
- [x] CONTRIBUTING drafted, references rule-format and PR-layering rules
- [x] MAINTAINERS drafted, lead maintainer listed
- [x] CODE_OF_CONDUCT adopts Contributor Covenant 2.1 by reference
- [x] SECURITY defines scope, channel, and disclosure window
- [x] CODEOWNERS routes to `@H31MDALLR`
- [x] PR template enforces rule-quality bar
- [x] Issue templates cover rule proposals and catalog bugs
- [x] `.gitignore` tightened
- [x] Stale `.cursor.zip.rename-me.zip` removed
- [ ] Repository visibility flipped to Public (post-merge)
- [ ] Tag `v2026.04.17.1` cut (post-merge)
