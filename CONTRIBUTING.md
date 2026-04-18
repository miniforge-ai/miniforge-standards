# Contributing to Miniforge Standards

Thanks for your interest in improving the standards. This repo is small and
opinionated — every rule here ends up in agent prompts and engineer-facing
docs across multiple downstream projects, so the bar for additions is high
and the bar for changes is real.

## Before you open a PR

1. **Open an issue first** for anything beyond a typo or a clarifying edit.
   Use the "Rule proposal" issue template for new or substantively-changed
   rules. The issue is where we agree on whether the rule belongs, what its
   Dewey code should be, and whether it's `alwaysApply: true`.
2. **Read the rule format spec**: [`meta/rule-format.mdc`](./meta/rule-format.mdc).
   Frontmatter discipline matters — the `description` field follows the
   `ACTION when TRIGGER to OUTCOME` pattern, and only `description`, `globs`,
   `dewey`, and `alwaysApply` belong in frontmatter.
3. **Pick the right Dewey range**:

   | Range | Category |
   |-------|----------|
   | 000-099 | Foundations |
   | 100-199 | Tools |
   | 200-299 | Languages |
   | 300-399 | Frameworks |
   | 400-499 | Testing |
   | 500-599 | Operations |
   | 600-699 | Documentation |
   | 700-799 | Workflows |
   | 800-899 | Project (reserved — do not add here) |
   | 900-999 | Meta |

   The 800-899 range is reserved for *consuming repos* to override or extend,
   not for shared standards.

## What makes a good rule

Rules are short, declarative, and machine-actionable. A reviewer should be
able to apply the rule mechanically to a code sample and get a clear
pass/fail. Things to favor:

- **Concise requirements** that each map to a single check.
- **Both a positive and a negative example.** The counter-example does the
  most work in agent prompts.
- **A real cost**: the rule should prevent a class of mistake we've actually
  seen, not enforce a personal preference. If you can't name the failure
  mode, the rule probably isn't ready.
- **Composability**: prefer linking to an existing foundational rule over
  duplicating its body inside a new rule.

## Pull request workflow

The repo follows its own [`workflows/pr-layering`](./workflows/pr-layering.mdc)
and [`workflows/pr-documentation`](./workflows/pr-documentation.mdc) rules.
In summary:

- Branch from `main`. Never branch from another in-flight branch.
- One stratum per PR. Adding a new rule, updating the index, and updating
  `CLAUDE.md` to reference it can be a single PR — but bundling unrelated
  rule changes is not OK.
- Keep PRs under ~400 lines of diff where possible.
- Pre-commit hooks must pass. If a hook fails, fix the cause; don't
  `--no-verify`. See [`workflows/pre-commit-discipline`](./workflows/pre-commit-discipline.mdc).
- Update both [`index.mdc`](./index.mdc) and [`CLAUDE.md`](./CLAUDE.md) when
  adding, renaming, or removing a rule.

A PR description should answer:

1. What rule (or what change to a rule) is this?
2. What failure mode does it prevent?
3. How will a reviewer or agent verify conformance?
4. What downstream repos are likely to be affected?

## Versioning and releases

The repo uses [DateVer](./workflows/datever.mdc) (`YYYY.MM.DD.N`). Material
changes should be summarized in `CHANGELOG.md` (created lazily on the first
release after this convention is adopted) and clearly noted in PR descriptions
so consuming repos can decide when to bump their submodule pin.

## Developer Certificate of Origin

By submitting a contribution to this repository, you certify that:

1. The contribution is your original work, or you have the right to submit
   it under the project's open-source license; and
2. You agree to license your contribution under the
   [Apache License 2.0](./LICENSE).

We don't require a separate CLA. Sign-off your commits with `git commit -s`
if you'd like to make this explicit.

## Code of conduct

Participation in this project is governed by the
[Contributor Covenant](./CODE_OF_CONDUCT.md). Report concerns to
[christopher@miniforge.ai](mailto:christopher@miniforge.ai).

## Maintainers

See [`MAINTAINERS.md`](./MAINTAINERS.md). Maintainers triage issues, review
PRs, and own the Dewey catalog. If you'd like to take ownership of a category
(e.g. `languages/swift`), open an issue describing your proposed scope.
