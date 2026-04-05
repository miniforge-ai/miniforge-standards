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
| Structure functions / avoid duplication | `foundations/code-quality` |
| Handle success/failure results | `foundations/result-handling` |
| Know where to put validation | `foundations/validation-boundaries` |
| Add user-facing strings | `foundations/localization` |
| Write tests | `testing/standards` |
| Write Clojure code | `languages/clojure` |
| Write Python code | `languages/python` |
| Write Rust code | `languages/rust` |
| Write Swift code | `languages/swift` |
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
  code-quality.mdc            dewey: "002"
  result-handling.mdc         dewey: "003"
  validation-boundaries.mdc   dewey: "004"
  simple-made-easy.mdc        dewey: "010"
  specification-standards.mdc dewey: "020"
  localization.mdc            dewey: "050"
languages/
  clojure.mdc                 dewey: "210"
  python.mdc                  dewey: "220"
  rust.mdc                    dewey: "230"
  swift.mdc                   dewey: "240"
frameworks/
  polylith.mdc                dewey: "300"
  kubernetes.mdc              dewey: "320"
testing/
  standards.mdc               dewey: "400"
workflows/
  git-branch-management.mdc   dewey: "710"
  pre-commit-discipline.mdc   dewey: "715"
  git-worktrees.mdc           dewey: "725"
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
000-099  Foundations     Architecture, design philosophy, code quality
  001      Stratified Design
  002      Code Quality (composable fns, pipelines, DRY)
  003      Result Handling (success?/failed? predicates, constructors)
  004      Validation Boundaries (schemas at interfaces/external only)
  010      Simple Made Easy
  020      Specification Standards
  050      Localization (i18n, en-US.edn)
100-199  Tools           Linters, formatters, build tools
200-299  Languages       Clojure, Python, JS/TS, Go, Rust, Swift
  210      Clojure (Polylith, stratified files, map access patterns)
  220      Python
  230      Rust (error handling, rule tables, context structs, linting)
  240      Swift (stratified views, access control, optionals, closures)
300-399  Frameworks      Polylith, K8s, web frameworks, databases
  300      Polylith
  320      Kubernetes
400-499  Testing         Unit, integration, E2E, code review
  400      Testing Standards (factory fns, same quality as prod)
500-599  Operations      CI/CD, monitoring, security
600-699  Documentation   API docs, architecture docs
700-799  Workflows       Git, PRs, releases
  710      Git Branch Management
  715      Pre-Commit Discipline
  721      PR Documentation
  722      PR Layering (DAG, stratified PRs)
  725      Git Worktrees
  730      Datever
800-899  Project         Reserved for project-specific overrides
  810      Copyright Header
900-999  Meta            Templates, indexes
  900      Rule Format
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
