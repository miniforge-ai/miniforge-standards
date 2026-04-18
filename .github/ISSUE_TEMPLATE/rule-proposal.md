---
name: Rule proposal
about: Propose a new rule or a substantive change to an existing rule
title: "[rule] "
labels: rule-proposal
---

## Rule slug

<!-- e.g. languages/go, workflows/release-notes. One rule per issue. -->

## Proposed Dewey code

<!-- See index.mdc. If you're not sure, propose a range and explain your
     reasoning; a maintainer will confirm or suggest an alternative. -->

## Problem

<!-- What failure mode do you see across codebases or agent outputs? Give
     one or two concrete examples. -->

## Proposed rule

<!-- Describe the rule in plain language. Avoid the full rule body for now —
     that belongs in the eventual PR. Focus on:
     - the requirement(s)
     - how a reviewer or agent would mechanically verify conformance
     - whether alwaysApply: true is justified (remember, always-applied rules
       occupy agent context for every prompt). -->

## Positive example

<!-- What does compliant code/content look like? -->

## Negative example

<!-- What does the rule catch? -->

## Scope

<!-- Which languages/frameworks/workflows are affected? Any downstream repos
     that would need to update in response? -->
