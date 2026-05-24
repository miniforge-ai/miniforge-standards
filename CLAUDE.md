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
| Replace a magic number or string | `foundations/named-constants` |
| Decide between code default and EDN config | `foundations/config-as-data` |
| Remove or refactor old code | `foundations/no-dead-code` |
| Add code without tests | `workflows/tests-with-code` |
| Run tasks in OCI containers | `foundations/runtime-*` (dewey 030–033) |
| Write tests | `testing/standards` |
| Write Clojure code | `languages/clojure` |
| Catch exceptions in Clojure (`try` vs `try+`) | `languages/clojure-exception-handling` |
| Write Python code | `languages/python` |
| Write Rust code | `languages/rust` |
| Write async/concurrent Rust | `languages/rust-async` |
| Touch `unsafe` in Rust | `languages/rust-unsafe` |
| Add Rust logging/tracing | `languages/rust-observability` |
| Design Rust wire formats / serialization | `languages/rust-wire-protocols` |
| Write Rust in a Miniforge product | `project/rust-miniforge-shape` |
| Write Swift code | `languages/swift` |
| Write browser JavaScript | `languages/javascript` |
| Write CSS | `languages/css` |
| Write HTML | `languages/html` |
| Work with Polylith | `frameworks/polylith` |
| Work with Kubernetes | `frameworks/kubernetes` |
| Pick a web architecture mode | `frameworks/web-architecture-mode` |
| Decide an API's surface class | `frameworks/api-surface-classes` |
| Browser security checklist | `frameworks/browser-security` |
| Build a Fulcro UI | `frameworks/fulcro` |
| Use Fulcro RAD | `frameworks/fulcro-rad` |
| Create a branch | `workflows/git-branch-management` |
| **Commit code** | **`workflows/pre-commit-discipline`** (CRITICAL) |
| Use git worktrees | `workflows/git-worktrees` |
| Plan a PR | `workflows/pr-layering` |
| Document a PR | `workflows/pr-documentation` |
| Version a release | `workflows/datever` |
| Add copyright header (OSS repos only) | `project/header-copyright` — **skip in proprietary repos; use `.thesium-standards/project/header-proprietary` there** |
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
  exceptions-as-data.mdc      dewey: "005"
  named-constants.mdc         dewey: "006"
  config-as-data.mdc          dewey: "007"
  no-dead-code.mdc            dewey: "008"
  simple-made-easy.mdc        dewey: "010"
  specification-standards.mdc dewey: "020"
  work-spec-authoring.mdc     dewey: "021"
  runtime-no-host-docker-socket.mdc      dewey: "030"
  runtime-require-rootless.mdc           dewey: "031"
  runtime-restrict-host-mounts.mdc       dewey: "032"
  runtime-require-image-digest-pin.mdc   dewey: "033"
  localization.mdc            dewey: "050"
languages/
  clojure.mdc                 dewey: "210"
  clojure-exception-handling.mdc  dewey: "211"
  python.mdc                  dewey: "220"
  rust.mdc                    dewey: "230"
  rust-async.mdc              dewey: "231"
  rust-unsafe.mdc             dewey: "232"
  rust-observability.mdc      dewey: "233"
  rust-wire-protocols.mdc     dewey: "234"
  swift.mdc                   dewey: "240"
  javascript.mdc              dewey: "250"
  css.mdc                     dewey: "260"
  html.mdc                    dewey: "270"
frameworks/
  polylith.mdc                dewey: "310"
  polylith-composition.mdc    dewey: "311"
  polylith-tool.mdc           dewey: "312"
  kubernetes.mdc              dewey: "320"
  web-architecture-mode.mdc   dewey: "330"
  browser-security.mdc        dewey: "331"
  api-surface-classes.mdc     dewey: "332"
  fulcro.mdc                  dewey: "340"
  fulcro-rad.mdc              dewey: "341"
testing/
  standards.mdc               dewey: "400"
workflows/
  git-branch-management.mdc   dewey: "710"
  pre-commit-discipline.mdc   dewey: "715"
  tests-with-code.mdc         dewey: "716"
  git-worktrees.mdc           dewey: "725"
  pr-documentation.mdc        dewey: "721"
  pr-layering.mdc             dewey: "722"
  datever.mdc                 dewey: "730"
project/
  header-copyright.mdc        dewey: "810"
  rust-miniforge-shape.mdc    dewey: "835"
meta/
  rule-format.mdc             dewey: "900"
```

## Dewey Ranges

```
000-099  Foundations     Architecture, design philosophy, code quality
  001      Stratified Design
  002      Code Quality (composable fns, pipelines, DRY)
  003      Result Handling (success?/failed? predicates, factory fns over hand-built maps)
  004      Validation Boundaries (schemas at interfaces/external only)
  005      Exceptions as data (anomalies in flow; throw only at absolute boundaries)
  006      Named Constants (no magic numbers/strings; intent-bearing `def` + docstring)
  007      Config as Data (operational values in .edn with Malli schema; defaults in code)
  008      No Dead Code (delete obsolete code in the same change; no legacy shims without removal plan)
  010      Simple Made Easy
  020      Specification Standards
  021      Work-Spec Authoring (priority, theme, testable criteria)
  030      Runtime: No Host Docker Socket (capsule isolation)
  031      Runtime: Require Rootless
  032      Runtime: Restrict Host Mounts
  033      Runtime: Require Image Digest Pin
  050      Localization (i18n, en-US.edn)
100-199  Tools           Linters, formatters, build tools
200-299  Languages       Clojure, Python, JS/TS, Go, Rust, Swift
  210      Clojure (Polylith, stratified files, map access patterns)
  211      Clojure exception handling (prefer `try+` / `throw+`; plain `try` allowed in three narrow cases — no-dep leaf catch, leaf inside try+, REPL)
  220      Python
  230      Rust (error handling, rule tables, context structs, linting, toolchain, deps)
  231      Rust async/concurrency (no blocking, no locks across .await, cancellation, bounded channels, task lifecycle)
  232      Rust unsafe discipline (deny by default, isolated behind safe APIs, SAFETY comments, profile-evidence required)
  233      Rust observability (tracing not println, correlation IDs via #[instrument], error source-chain preservation, system-catalog log strings)
  234      Rust wire protocols (wire/domain separation, versioned envelopes, validate-on-deserialize, append-only audit ratchet)
  240      Swift (stratified views, access control, optionals, closures)
  250      JavaScript (browser; sparse use, no globals, ES modules, safe DOM)
  260      CSS (design tokens, semantic class names, shallow selectors, focus)
  270      HTML (semantic elements, buttons-vs-links, labelled forms)
300-399  Frameworks      Polylith, K8s, web frameworks, databases
  310      Polylith
  311      Polylith Composition (bricks/bases/interfaces, validation gates, CI scope)
  312      Polylith Tool (preflight reasoning, canonical workflow, agent-role operational reqs)
  320      Kubernetes
  330      Web Architecture Mode (Fulcro for stateful UI, server HTML for simple pages)
  331      Browser Security (server-enforced authz, CSRF, no secrets, blast-radius UI)
  332      API Surface Classes (internal vs first-party cross-language vs customer extension)
  340      Fulcro (queries, idents, normalisation, mutations, loads, domain naming)
  341      Fulcro RAD (optional, for CRUD; never forced onto bespoke workflow UI)
400-499  Testing         Unit, integration, E2E, code review
  400      Testing Standards (factory fns, same quality as prod)
500-599  Operations      CI/CD, monitoring, security
600-699  Documentation   API docs, architecture docs
700-799  Workflows       Git, PRs, releases
  710      Git Branch Management
  715      Pre-Commit Discipline
  716      Tests With Code (no-test-diff PRs must claim pure restructuring)
  721      PR Documentation
  722      PR Layering (DAG, stratified PRs)
  725      Git Worktrees
  730      Datever
800-899  Project         Reserved for project-specific overrides
  810      Copyright Header
  835      Rust Miniforge Shape (typed workflow state, PolicyDecision as value, structured findings, adapters-behind-traits, append-only evidence)
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

Any repository that wants this rule set consumes it as a git submodule at
`.standards/`:

```bash
git submodule add git@github.com:miniforge-ai/miniforge-standards.git .standards
```

Project-specific additions layer on top via each repo's own policy-pack
mechanism (`:policy-packs {:extra-search-paths [...]}`) — they do not
override or modify this shared repo.
