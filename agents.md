# Miniforge.ai Engineering Standards — Agent Guide

This repository is the single source of truth for engineering rules, conventions,
and agent knowledge shared across all Miniforge.ai repositories. It is consumed
as a git submodule at `.standards/` in each repo.

## How to Use These Standards

1. **Before writing code** — check relevant language and framework rules
2. **Before branching** — consult `workflows/git-branch-management`
3. **Before committing** — follow `workflows/pre-commit-discipline` (CRITICAL)
4. **Before opening a PR** — follow `workflows/pr-layering` and `workflows/pr-documentation`
5. **When creating a new rule** — follow `meta/rule-format` with Dewey frontmatter
6. **When in doubt** — apply `foundations/stratified-design` and `foundations/simple-made-easy`

## Quick Reference

| Need to... | Rule file |
|------------|-----------|
| Understand architecture | `foundations/stratified-design` |
| Apply design philosophy | `foundations/simple-made-easy` |
| Write Clojure code | `languages/clojure` |
| Write Python code | `languages/python` |
| Work with Polylith | `frameworks/polylith` |
| Work with Kubernetes | `frameworks/kubernetes` |
| Create a branch | `workflows/git-branch-management` |
| **Commit code** | **`workflows/pre-commit-discipline`** (CRITICAL) |
| Use git worktrees | `workflows/git-worktrees` |
| Plan a PR | `workflows/pr-layering` |
| Document a PR | `workflows/pr-documentation` |
| Version a release | `workflows/datever` |
| Add copyright header | `project/header-copyright` |
| Create a new rule | `meta/rule-format` |

## Rules Catalog

Rules live at the root of this repo with slug-based filenames. Dewey codes are
in frontmatter (`dewey: "NNN"`), not in filenames or paths.

```
.standards/                         # submodule root in consuming repos
├── index.mdc                       # Master catalog (dewey: "000")
├── foundations/
│   ├── stratified-design.mdc       # dewey: "001"
│   ├── simple-made-easy.mdc        # dewey: "010"
│   └── specification-standards.mdc # dewey: "020"
├── languages/
│   ├── clojure.mdc                 # dewey: "210"
│   └── python.mdc                  # dewey: "220"
├── frameworks/
│   ├── polylith.mdc                # dewey: "300"
│   └── kubernetes.mdc              # dewey: "320"
├── workflows/
│   ├── git-branch-management.mdc   # dewey: "710"
│   ├── pre-commit-discipline.mdc   # dewey: "715"
│   ├── git-worktrees.mdc           # dewey: "720"
│   ├── pr-documentation.mdc        # dewey: "721"
│   ├── pr-layering.mdc             # dewey: "722"
│   └── datever.mdc                 # dewey: "730"
├── project/
│   └── header-copyright.mdc        # dewey: "810"
└── meta/
    └── rule-format.mdc             # dewey: "900"
```

## Dewey Classification

| Range | Category |
|-------|----------|
| 000-099 | Foundations — architecture, design philosophy |
| 100-199 | Tools — linters, formatters, build tools |
| 200-299 | Languages — Clojure, Python, JS/TS, Go, Rust |
| 300-399 | Frameworks — Polylith, K8s, web, databases |
| 400-499 | Testing — unit, integration, E2E, code review |
| 500-599 | Operations — CI/CD, monitoring, security |
| 600-699 | Documentation — API docs, architecture docs |
| 700-799 | Workflows — git, PRs, releases |
| 800-899 | Project — reserved for project-specific rules |
| 900-999 | Meta — templates, indexes |

## Core Principles (Always Apply)

### Stratified Design
- Dependencies flow **downward only**: Adapters → Application → Domain → Foundations
- No cycles in the import graph; no layer reaches up
- Pure core — the Domain layer has no I/O

### Simple Made Easy
- Prefer **simple** (unbraided) over **easy** (familiar)
- Values over state; data over syntax; functions over methods
- Centralize policy as data, not scattered conditionals

### PR Discipline
- Each PR = one stratum, <400 lines, independently mergeable
- Branch from `main` (never from another feature branch)
- **NEVER** bypass pre-commit hooks — investigate failures, fix root causes

### Specification-Driven Development
- Normative specs (N-series) are implementation contracts
- Specs are extracted from strategy, not reverse-engineered from code
- Code conforms to specs; specs do not describe code

## Consuming Repos

Add this repo as a submodule at `.standards/`:

```bash
git submodule add git@github.com:miniforge-ai/miniforge-standards.git .standards
```

Each repo's `CLAUDE.md` should reference `.standards/agents.md` and
`.standards/claude.md`. Project-specific overrides go in the repo's own
`project/` rules directory — never in this shared repo.

| Repository | Product |
|------------|---------|
| `miniforge` | Miniforge Core + Software Factory |
| `data-foundry` | Data pipeline framework |
| `miniforge-fleet-specs` | Enterprise fleet extensions |
| `risk-core` | Risk analytics kernel |
| `risk-data-plane` | Risk data infrastructure |
| `risk-apple-app` | Apple platform risk client |
| `marketwatch` | Market data platform |
