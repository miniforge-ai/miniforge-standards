# fix: govern normative specification extensions

## Overview

Updates the `specification-standards` rule to match the repository's
established core-plus-extension model: N1-N6 are the universal core, while
N7+ specifications govern only the products and capabilities named by the
authoritative specification index.

## Motivation

The existing rule forbids every normative specification after N6. That
conflicts with live Miniforge specifications and with standards rules that
already reference extension specifications such as N11. Agents following the
rule can therefore ignore applicable contracts or incorrectly delete valid
extension work.

## Changes in Detail

- Preserve N1-N6 as the universal core specification set.
- Define how indexed extension specifications become applicable.
- Require new extensions to declare their scope and relationships to the core.
- Replace the obsolete blanket prohibition on new normative files with checks
  against duplicate, unindexed, or unapproved contracts.
- Update the rule catalog description.

## Testing Plan

Docs-only change. Validate metadata, catalog references, internal terminology,
and the final diff; run repository-provided checks if present.

## Deployment Plan

Merge to `main`; consuming repositories receive the rule on their next
standards submodule update.

## Related Issues/PRs

- Miniforge N7 Operational Policy Synthesis and Verification implementation

## Checklist

- [x] Rule follows `meta/rule-format` structure
- [x] Core and extension applicability is unambiguous
- [x] Catalog description matches the rule
- [x] Repository checks pass (docs-only repository; diff and reference checks)
