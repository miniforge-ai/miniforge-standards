# Miniforge.ai Engineering Standards

This repository is the single source of truth for engineering rules, conventions,
and agent knowledge across all Miniforge.ai repositories. It is consumed as a
git submodule at `.standards/` in each repo.

## How Consuming Repos Use This

Each repo includes this as a submodule and has its own root `CLAUDE.md` that
imports these shared rules plus repo-specific additions:

```
my-repo/
  .standards/          -> git submodule (this repo)
  .cursor/rules/       -> symlink to .standards/.cursor/rules/
  CLAUDE.md            -> repo-specific, references .standards/CLAUDE.md
```

## Quick Reference

| Need to... | Consult |
|------------|---------|
| Understand architecture | `001-stratified-design`, `010-simple-made-easy` |
| Write Clojure code | `210-clojure` |
| Write Python code | `220-python` |
| Work with Polylith | `310-polylith` |
| Work with Kubernetes | `320-kubernetes` |
| Create a branch | `710-git-branch-management` |
| **Commit code** | **`715-pre-commit-discipline`** (CRITICAL) |
| Use git worktrees | `720-git-worktrees` |
| Plan a PR | `720-pr-layering` |
| Document a PR | `721-pr-documentation` |
| Version a release | `730-datever` |
| Add copyright header | `810-header-copyright` |
| Create a new rule | `900-rule-format` |

## Rules Catalog (Dewey Classification)

Rules live in `.cursor/rules/` with Dewey-style numeric prefixes.

```
.cursor/rules/
├── 000-index.mdc                        # Master catalog
├── 000-foundations/
│   ├── 001-stratified-design.mdc        # One-way DAG dependencies
│   ├── 010-simple-made-easy.mdc         # Simplicity over familiarity
│   └── 020-specification-standards.mdc  # Normative spec conventions
├── 200-languages/
│   ├── 210-clojure.mdc                  # Clojure + Polylith conventions
│   └── 220-python.mdc                   # Python + Poetry conventions
├── 300-frameworks/
│   ├── 310-polylith.mdc                # Polylith workspace rules
│   └── 320-kubernetes.mdc              # K8s/Kustomize/ArgoCD
├── 700-workflows/
│   ├── 710-git-branch-management.mdc   # Branch from main, single-purpose
│   ├── 715-pre-commit-discipline.mdc   # NEVER bypass hooks (CRITICAL)
│   ├── 720-git-worktrees.mdc           # AI agent worktree isolation
│   ├── 720-pr-layering.mdc             # PRs by stratum, <400 lines
│   ├── 721-pr-documentation.mdc        # PR living docs
│   └── 730-datever.mdc                 # DateVer versioning
├── 800-project/
│   └── 810-header-copyright.mdc        # Miniforge.ai copyright header
└── 900-meta/
    └── 900-rule-format.mdc             # Template for new rules
```

### Dewey Ranges

```
000-099  Foundations     Architecture, design philosophy
100-199  Tools           Linters, formatters, build tools
200-299  Languages       Clojure, Python, JS/TS, Go, Rust
300-399  Frameworks      K8s, Polylith, web frameworks, databases
400-499  Testing         Unit, integration, E2E, code review
500-599  Operations      CI/CD, monitoring, security
600-699  Documentation   API docs, architecture docs
700-799  Workflows       Git, PRs, releases
800-899  Project         Reserved for project-specific overrides
900-999  Meta            Templates, indexes
```

## Core Principles (Always Apply)

### Stratified Design
- Dependencies flow **downward only**: Adapters -> Application -> Domain -> Foundations
- No cycles in the import graph
- Pure core (Domain layer has no I/O)

### Simple Made Easy
- Prefer **simple** (unbraided) over **easy** (familiar)
- Values over state; data over syntax; functions over methods
- Centralize policy as data, not scattered conditionals

### PR Discipline
- Each PR = one stratum, <400 lines, independently mergeable
- Branch from main (never from feature branches unless explicit)
- **NEVER** bypass pre-commit hooks

### Specification-Driven
- Normative specs (N-series) are implementation contracts
- Specs are extracted from strategic documents, not from code
- Code conforms to specs; specs do not describe code

## For Agents: How to Use These Standards

1. **Before writing code**: Check relevant rules (language, framework, architecture)
2. **Before creating branches**: Consult `710-git-branch-management`
3. **Before committing**: Follow `715-pre-commit-discipline` (CRITICAL)
4. **Before opening PRs**: Follow `720-pr-layering` and `721-pr-documentation`
5. **When creating rules**: Follow `900-rule-format` with Dewey numbering
6. **When in doubt**: Apply `001-stratified-design` and `010-simple-made-easy`

## Consuming Repos

This standards repo is used across:

| Repository | Product | Status |
|------------|---------|--------|
| `miniforge` | Miniforge Core + Software Factory | Active |
| `data-foundry` | Data pipeline framework | Active |
| `miniforge-fleet-specs` | Enterprise fleet extensions | Active |
| `risk-core` | Risk analytics kernel | Active |
| `risk-data-plane` | Risk data infrastructure | Active |
| `risk-apple-app` | Apple platform risk client | Active |
| `marketwatch` | Market data platform | Onboarding |

Project-specific overrides go in each repo's own `800-project/` rules or
repo-level `CLAUDE.md`, never in this shared repo.
