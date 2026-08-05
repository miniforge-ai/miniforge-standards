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
| Understand architecture (stratified design) | `foundations/stratified-design` |
| Enforce module dependency direction (the Dependency Rule) | `foundations/layered-architecture` |
| Apply design philosophy | `foundations/simple-made-easy` |
| Structure functions / avoid duplication | `foundations/code-quality` |
| Handle success/failure results | `foundations/result-handling` |
| Know where to put validation | `foundations/validation-boundaries` |
| **Propagate errors as values; throw only at absolute boundaries** | **`foundations/exceptions-as-data`** (CRITICAL) |
| Add user-facing strings | `foundations/localization` |
| Replace a magic number or string | `foundations/named-constants` |
| Decide between code default and EDN config | `foundations/config-as-data` |
| Remove or refactor old code | `foundations/no-dead-code` |
| Decide whether to write a comment (WHY, not WHAT) | `foundations/self-documenting-code` |
| Decide how much to document / where docs live (schemas, length, narrative) | `documentation/documentation-discipline` |
| Write a normative specification | `foundations/specification-standards` |
| Author a work spec or task brief | `foundations/work-spec-authoring` |
| Run tasks in OCI containers | `foundations/runtime-*` (dewey 030–033) |
| Write tests | `testing/standards` |
| Understand when a code change can omit test changes | `workflows/tests-with-code` |
| Write Clojure code | `languages/clojure` |
| Catch exceptions in Clojure (`try` vs `try+`) | `languages/clojure-exception-handling` |
| Resolve a dependency (never `requiring-resolve`) | `languages/clojure-no-requiring-resolve` |
| Write Python code | `languages/python` |
| Structure a Python package as a stratified component | `languages/python-stratified-components` |
| Write Rust code | `languages/rust` |
| Write async/concurrent Rust | `languages/rust-async` |
| Touch `unsafe` in Rust | `languages/rust-unsafe` |
| Add Rust logging/tracing | `languages/rust-observability` |
| Design Rust wire formats / serialization | `languages/rust-wire-protocols` |
| Use typed boundary commands / pipeline selection in Rust | `languages/rust-boundary-commands` |
| Structure a Rust file with Layer headings (stratified) | `languages/rust-stratified-modules` |
| Write Rust in a Miniforge product | `project/rust-miniforge-shape` |
| Write Swift code | `languages/swift` |
| Structure a Swift file with Layer headings (stratified) | `languages/swift-stratified-files` |
| Write browser JavaScript | `languages/javascript` |
| Write CSS | `languages/css` |
| Write HTML | `languages/html` |
| Work with Polylith | `frameworks/polylith` |
| Compose Polylith bricks / validate workspace | `frameworks/polylith-composition` |
| Use the Polylith CLI tool | `frameworks/polylith-tool` |
| Work with Kubernetes | `frameworks/kubernetes` |
| Pick a web architecture mode | `frameworks/web-architecture-mode` |
| Decide an API's surface class | `frameworks/api-surface-classes` |
| Browser security checklist | `frameworks/browser-security` |
| Build a Fulcro UI | `frameworks/fulcro` |
| Use Fulcro RAD | `frameworks/fulcro-rad` |
| Create a branch | `workflows/git-branch-management` |
| **Commit code** | **`workflows/pre-commit-discipline`** (CRITICAL) |
| **Review a PR before push** | **`workflows/code-review-rigor`** (CRITICAL — happy-path trace, bootstrap order, default-value scrutiny, compile-test claims, intent over symptoms, refactor-not-exempt) |
| Use git worktrees | `workflows/git-worktrees` |
| Plan a PR | `workflows/pr-layering` |
| Document a PR | `workflows/pr-documentation` |
| Version a release | `workflows/datever` |
| Add a build / launch / package / deploy task | `workflows/bb-over-shell` — a bb task in `bb.edn`; **never** a new `.sh` or `.py` |
| Add copyright header (OSS repos only) | `project/header-copyright` — **skip in proprietary repos; use `.thesium-standards/project/header-proprietary` there** |
| Create a new rule | `meta/rule-format` |
| Design or review a user-facing UI surface (cross-product) | `design/ux-general.md` |
| Design or review Miniforge product UI | `design/ux-miniforge.md` |
| Design or review Thesium product UI | `design/ux-thesium.md` |

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
│   ├── exceptions-as-data.mdc           # dewey: "005"  alwaysApply: true
│   ├── named-constants.mdc              # dewey: "006"  alwaysApply: true
│   ├── config-as-data.mdc               # dewey: "007"  alwaysApply: true
│   ├── no-dead-code.mdc                 # dewey: "008"  alwaysApply: true
│   ├── self-documenting-code.mdc        # dewey: "009"  alwaysApply: true
│   ├── simple-made-easy.mdc             # dewey: "010"  alwaysApply: true
│   ├── layered-architecture.mdc         # dewey: "011"  alwaysApply: true
│   ├── specification-standards.mdc      # dewey: "020"
│   ├── work-spec-authoring.mdc          # dewey: "021"
│   ├── runtime-no-host-docker-socket.mdc  # dewey: "030"  alwaysApply: true
│   ├── runtime-require-rootless.mdc       # dewey: "031"  alwaysApply: true
│   ├── runtime-restrict-host-mounts.mdc   # dewey: "032"  alwaysApply: true
│   ├── runtime-require-image-digest-pin.mdc # dewey: "033"  alwaysApply: true
│   └── localization.mdc                 # dewey: "050"  alwaysApply: true
├── languages/
│   ├── clojure.mdc                      # dewey: "210"  alwaysApply: true
│   ├── clojure-exception-handling.mdc   # dewey: "211"  alwaysApply: true
│   ├── clojure-no-requiring-resolve.mdc # dewey: "212"  globs: clj/cljc
│   ├── python.mdc                       # dewey: "220"
│   ├── python-stratified-components.mdc # dewey: "221"
│   ├── rust.mdc                         # dewey: "230"
│   ├── rust-async.mdc                   # dewey: "231"
│   ├── rust-unsafe.mdc                  # dewey: "232"
│   ├── rust-observability.mdc           # dewey: "233"
│   ├── rust-wire-protocols.mdc          # dewey: "234"
│   ├── rust-boundary-commands.mdc       # dewey: "235"
│   ├── rust-stratified-modules.mdc      # dewey: "236"
│   ├── swift.mdc                        # dewey: "240"
│   ├── swift-stratified-files.mdc       # dewey: "241"
│   ├── javascript.mdc                   # dewey: "250"
│   ├── css.mdc                          # dewey: "260"
│   └── html.mdc                         # dewey: "270"
├── frameworks/
│   ├── polylith.mdc                     # dewey: "310"
│   ├── polylith-composition.mdc         # dewey: "311"
│   ├── polylith-tool.mdc                # dewey: "312"
│   ├── kubernetes.mdc                   # dewey: "320"
│   ├── web-architecture-mode.mdc        # dewey: "330"
│   ├── browser-security.mdc             # dewey: "331"
│   ├── api-surface-classes.mdc          # dewey: "332"
│   ├── fulcro.mdc                       # dewey: "340"
│   └── fulcro-rad.mdc                   # dewey: "341"
├── testing/
│   └── standards.mdc                    # dewey: "400"  alwaysApply: true
├── documentation/
│   └── documentation-discipline.mdc     # dewey: "600"  alwaysApply: true
├── workflows/
│   ├── git-branch-management.mdc        # dewey: "710"  alwaysApply: true
│   ├── pre-commit-discipline.mdc        # dewey: "715"  alwaysApply: true
│   ├── tests-with-code.mdc              # dewey: "716"  alwaysApply: true
│   ├── code-review-rigor.mdc            # dewey: "720"  alwaysApply: true
│   ├── pr-documentation.mdc             # dewey: "721"  alwaysApply: true
│   ├── pr-layering.mdc                  # dewey: "722"  alwaysApply: true
│   ├── git-worktrees.mdc                # dewey: "725"
│   ├── datever.mdc                      # dewey: "730"
│   └── bb-over-shell.mdc                # dewey: "740"
├── project/
│   ├── header-copyright.mdc             # dewey: "810"
│   └── rust-miniforge-shape.mdc         # dewey: "835"
└── meta/
    └── rule-format.mdc                  # dewey: "900"
```

_`design/` is intentionally excluded from the catalog above — those files are non-Dewey UX guidelines; see [Supplementary Guidelines](#supplementary-guidelines) below._

## Supplementary Guidelines

These files live in `design/` and are not Dewey-indexed rules — they are
UX/design guidelines consumed by agents building product UI. (They are not
listed in the Rules Catalog tree above.) They are not in `.mdc` format and are
not auto-injected into prompts; **load them
explicitly when designing or reviewing any user-facing surface.**

| File | Scope |
|------|-------|
| `design/ux-general.md` | Cross-product UX principles (typography, spacing, motion, accessibility) |
| `design/ux-miniforge.md` | Miniforge-specific visual language and component conventions |
| `design/ux-thesium.md` | Thesium product-line design identity and interaction patterns |

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

### Autonomy Policy
- If you're working towards goals, do not end your turn. This allows for continuous autonomous work.
- The user will interrupt you when required, but they will mostly provide steering messages.
- Do not pester the user by ending your turn after a unit of work, as that requires them to keep nudging you to keep working.
- You must continue working autonomously towards any known objectives until the user interrupts you. Do not end your turn until there is absolutely nothing left to do.
- Do not ask the user to "call it a day" or "take this up tomorrow". If you have goals or a plan then keep going.

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
- **No raw strings** in any emitted output — views, log calls,
  metric descriptions, anomaly messages all flow through a catalog
- User-facing strings → `messages/en-US.edn` (per-component)
- Developer-facing strings (logs, telemetry, anomaly text bound for
  observability) → `messages/system.edn` (per-component)
- Use `(msg/t :key)` regardless of catalog
- See: `foundations/localization`

### Testing
- **Factory functions** over inline map construction
- **Same standards** as production code (no magic numbers, no nested conditionals)
- See: `testing/standards`

### Stratified Design
- Build each layer as a small **language** on the one below; one layer's constructs are the next layer's primitives (SICP §2.2.4)
- A one-way **DAG of abstraction** — it branches; not a fixed layer stack or a linear chain
- Strata are discovered from the problem; name a layer for what it means

### Layered Architecture (the Dependency Rule)
- One coarse, module-level instance of stratified design: dependencies flow **inward/downward only** — Adapters → Application → Domain → Foundations
- No cycles in the import graph; no module reaches up
- Pure core — the Domain layer has no I/O; inject effects at the edges

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
- Implementations conform to N1-N6 plus amendments/extensions assigned by `specs/SPEC_INDEX.md`
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
