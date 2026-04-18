<!--
Thanks for contributing to miniforge-standards.

Before opening the PR, please confirm:
- A companion issue exists and the Dewey code was agreed on (unless this is
  a typo or clarifying edit).
- Only one stratum of change is bundled here (see workflows/pr-layering).
- index.mdc and CLAUDE.md are updated if a rule was added, renamed, or removed.
-->

## What is this change?

<!-- One or two sentences. Name the rule slug and Dewey code. -->

## What failure mode does it prevent?

<!-- Describe the class of mistake this rule catches. "Personal preference"
     is not a sufficient answer. -->

## How does a reviewer or agent verify conformance?

<!-- Describe the test. Ideally: a positive example, a negative example,
     and the mechanical check that distinguishes them. -->

## Downstream impact

<!-- List consuming repos likely to be affected, or write "none expected". -->

## Checklist

- [ ] Frontmatter follows `meta/rule-format.mdc`
- [ ] `index.mdc` updated (if rule added/renamed/removed)
- [ ] `CLAUDE.md` quick-reference updated (if rule added/renamed/removed)
- [ ] Both positive and negative examples included in the rule body
- [ ] PR is under ~400 lines of diff
- [ ] Pre-commit hooks pass without `--no-verify`

## Related issue(s)

<!-- Link the rule-proposal issue. e.g. Closes #123 -->
