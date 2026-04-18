# Miniforge Standards

Shared engineering standards, conventions, and agent-facing rules used across
the Miniforge.ai ecosystem. Designed to be consumed as a git submodule at
`.standards/` in any repo that wants to inherit the same architectural
discipline, PR workflow, and language conventions.

This repository is open source so that anyone forking [Miniforge][miniforge]
or building inside the ecosystem can pull in the same baseline rules without
re-deriving them.

[miniforge]: https://github.com/miniforge-ai/miniforge

## What's in here

A catalog of `.mdc` rule files classified using a Dewey-decimal scheme. Each
rule is a short, focused, machine-readable document with a frontmatter header
that declares its category (`dewey`), description, and applicability.

| Range | Category | Examples |
|-------|----------|----------|
| 000-099 | Foundations | Stratified design, Simple Made Easy, code quality |
| 100-199 | Tools | Linters, formatters, build tools |
| 200-299 | Languages | Clojure, Python, Rust, Swift |
| 300-399 | Frameworks | Polylith, Kubernetes |
| 400-499 | Testing | Test conventions and quality bars |
| 500-599 | Operations | CI/CD, monitoring, security |
| 600-699 | Documentation | API docs, architecture docs |
| 700-799 | Workflows | Git, PRs, releases |
| 800-899 | Project | Reserved for project-specific rules |
| 900-999 | Meta | Templates, indexes, the rule format itself |

The full catalog with file paths and `alwaysApply` flags lives in
[`index.mdc`](./index.mdc). Agent-oriented entry points are
[`CLAUDE.md`](./CLAUDE.md) and [`agents.md`](./agents.md).

## Using this in your repo

Add it as a submodule at `.standards/`:

```bash
git submodule add https://github.com/miniforge-ai/miniforge-standards.git .standards
git submodule update --init --recursive
```

Then point your repo's `CLAUDE.md` (or equivalent agent prompt) at the shared
rules:

```markdown
# My Repo

See [`.standards/CLAUDE.md`](./.standards/CLAUDE.md) for shared engineering
standards. Repo-specific overrides live in `./project/`.
```

Project-specific overrides go in your own repo's `project/` directory, never
inside this submodule. That keeps the shared standards free of any one
project's quirks.

To pull updates later:

```bash
git submodule update --remote .standards
git add .standards
git commit -m "chore: bump .standards"
```

## How rules are organized

Rules live at the repo root in category folders (`foundations/`,
`languages/`, `frameworks/`, `testing/`, `workflows/`, `project/`, `meta/`).
The Dewey code lives in the file's frontmatter, not in its filename or path
— this lets us renumber, reorganize, or add intermediate codes without
breaking links.

A rule looks like this:

```mdc
---
dewey: "002"
description: ACTION when TRIGGER to OUTCOME
globs: **/*.clj
alwaysApply: true
---

# Code Quality

## Context
- When to apply this rule

## Requirements
- Concise, actionable items
- Each requirement must be testable

## Examples
<example>Good example</example>
<example type="invalid">Counter-example</example>
```

The full rule format spec is in [`meta/rule-format.mdc`](./meta/rule-format.mdc).

## Versioning

This repo uses [DateVer](./workflows/datever.mdc): `YYYY.MM.DD.N`. Tags are
`v{version}` (e.g. `v2026.04.17.1`). Versions sort chronologically, which
matters more for a continuously-updated standards corpus than semantic
"breaking change" signaling. Material changes are called out in
[`CHANGELOG.md`](./CHANGELOG.md) when present, and in pull request descriptions
otherwise.

## Contributing

Proposals to add, modify, or retire a rule are welcome. Read
[`CONTRIBUTING.md`](./CONTRIBUTING.md) for the workflow — in short: open an
issue first to discuss the rule's purpose and Dewey placement, then submit a
PR following the format in `meta/rule-format.mdc`.

This repository follows the [Contributor Covenant](./CODE_OF_CONDUCT.md).
Maintainership is documented in [`MAINTAINERS.md`](./MAINTAINERS.md).

## License

Licensed under the [Apache License 2.0](./LICENSE). See [`NOTICE`](./NOTICE)
for attribution requirements.

Copyright 2025-2026 Christopher Lester (christopher@miniforge.ai).
