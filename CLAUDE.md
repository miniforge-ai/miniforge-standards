# Miniforge.ai Engineering Standards

This repository is the single source of truth for engineering rules, conventions,
and agent knowledge across all Miniforge.ai repositories. It is consumed as a
git submodule at `.standards/` in each repo.

## How Consuming Repos Use This

```
my-repo/
  .standards/   # git submodule (this repo)
  CLAUDE.md     # repo-specific, references .standards/CLAUDE.md and agents.md
  agents.md     # repo-specific, references .standards/agents.md
```

The knowledge loader reads rules from `.standards/` by default.
Project-specific rules go in a local `project/` directory alongside `.standards/`.

## Quick Reference

| Need to... | Consult |
|------------|---------|
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

Rules live at the repo root with slug filenames. Dewey codes are in frontmatter
(`dewey: "NNN"`), not encoded in filenames or paths.

```
foundations/
  stratified-design.mdc       dewey: "001"
  simple-made-easy.mdc        dewey: "010"
  specification-standards.mdc dewey: "020"
languages/
  clojure.mdc                 dewey: "210"
  python.mdc                  dewey: "220"
frameworks/
  polylith.mdc                dewey: "300"
  kubernetes.mdc              dewey: "320"
workflows/
  git-branch-management.mdc   dewey: "710"
  pre-commit-discipline.mdc   dewey: "715"
  git-worktrees.mdc           dewey: "720"
  pr-documentation.mdc        dewey: "721"
  pr-layering.mdc             dewey: "722"
  datever.mdc                 dewey: "730"
project/
  header-copyright.mdc        dewey: "810"
meta/
  rule-format.mdc             dewey: "900"
```

## Dewey Ranges

```
000-099  Foundations     Architecture, design philosophy
100-199  Tools           Linters, formatters, build tools
200-299  Languages       Clojure, Python, JS/TS, Go, Rust
300-399  Frameworks      Polylith, K8s, web frameworks, databases
400-499  Testing         Unit, integration, E2E, code review
500-599  Operations      CI/CD, monitoring, security
600-699  Documentation   API docs, architecture docs
700-799  Workflows       Git, PRs, releases
800-899  Project         Reserved for project-specific overrides
900-999  Meta            Templates, indexes
```

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

### Specification-Driven
- Normative specs (N-series) are implementation contracts
- Specs are extracted from strategic documents, not from code
- Code conforms to specs; specs do not describe code

## Consuming Repos

| Repository | Product |
|------------|---------|
| `miniforge` | Miniforge Core + Software Factory |
| `data-foundry` | Data pipeline framework |
| `miniforge-fleet-specs` | Enterprise fleet extensions |
| `risk-core` | Risk analytics kernel |
| `risk-data-plane` | Risk data infrastructure |
| `risk-apple-app` | Apple platform risk client |
| `marketwatch` | Market data platform |

Project-specific overrides go in each repo's own `project/` rules directory,
never in this shared repo.
