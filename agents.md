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
| Structure functions / avoid duplication | `foundations/code-quality` |
| Handle success/failure results | `foundations/result-handling` |
| Know where to put validation | `foundations/validation-boundaries` |
| Add user-facing strings | `foundations/localization` |
| Write tests | `testing/standards` |
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
.standards/                              # submodule root in consuming repos
├── index.mdc                            # Master catalog (dewey: "000")
├── foundations/
│   ├── stratified-design.mdc            # dewey: "001"  alwaysApply: true
│   ├── code-quality.mdc                 # dewey: "002"  alwaysApply: true
│   ├── result-handling.mdc              # dewey: "003"  alwaysApply: true
│   ├── validation-boundaries.mdc        # dewey: "004"  alwaysApply: true
│   ├── simple-made-easy.mdc             # dewey: "010"  alwaysApply: true
│   ├── specification-standards.mdc      # dewey: "020"
│   └── localization.mdc                 # dewey: "050"  alwaysApply: true
├── languages/
│   ├── clojure.mdc                      # dewey: "210"  alwaysApply: true
│   └── python.mdc                       # dewey: "220"
├── frameworks/
│   ├── polylith.mdc                     # dewey: "300"
│   ├── polylith-composition.mdc         # dewey: "311"
│   ├── polylith-tool.mdc                # dewey: "312"
│   └── kubernetes.mdc                   # dewey: "320"
├── testing/
│   └── standards.mdc                    # dewey: "400"  alwaysApply: true
├── workflows/
│   ├── git-branch-management.mdc        # dewey: "710"
│   ├── pre-commit-discipline.mdc        # dewey: "715"  alwaysApply: true
│   ├── pr-documentation.mdc             # dewey: "721"
│   ├── pr-layering.mdc                  # dewey: "722"  alwaysApply: true
│   ├── git-worktrees.mdc                # dewey: "725"
│   └── datever.mdc                      # dewey: "730"
├── project/
│   └── header-copyright.mdc             # dewey: "810"  alwaysApply: true
└── meta/
    └── rule-format.mdc                  # dewey: "900"
```

## Dewey Classification

| Range | Category |
|-------|----------|
| 000-099 | Foundations — architecture, design philosophy, code quality |
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

All rules marked `alwaysApply: true` are pre-injected into every agent prompt.
The following are the highest-priority, always-on principles:

### Code Structure
- **Composable pipelines** — every function reads as a pipeline; compose small fns up
- **No nested conditionals** — max one level; use `cond`, guard clauses, or dispatch maps
- **DRY** — shared logic goes in a component and is imported via its interface
- See: `foundations/code-quality`

### Result Handling
- **Use predicates** — `success?`/`failed?`, never `(:success? result)`
- **Use constructors** — `(schema/success ...)`, never `{:success? true ...}`
- See: `foundations/result-handling`

### Validation
- **Schemas at boundaries only** — `interface.clj` and external entry points
- **Trust internal data** — no validation inside components
- See: `foundations/validation-boundaries`

### Localization
- **No raw strings** in views/templates — use `(msg/t :key)` and `en-US.edn`
- See: `foundations/localization`

### Testing
- **Factory functions** over inline map construction
- **Same standards** as production code (no magic numbers, no nested conditionals)
- See: `testing/standards`

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
- PR dependencies form a DAG — never a monolith
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
`.standards/CLAUDE.md`. Project-specific additions layer on top via each
repo's own policy-pack mechanism — never by modifying this shared repo.
