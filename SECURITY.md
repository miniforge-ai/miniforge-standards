# Security Policy

This repository contains documentation and agent-prompt rules. It does not
ship executable code or binaries, so the practical security surface is
small. Even so, we take reports seriously.

## What counts as a security issue here

- A rule that could plausibly induce downstream agents or engineers to write
  insecure code (e.g. a recommendation that disables validation, leaks
  secrets, or encourages unsafe defaults).
- A supply-chain concern affecting how this repo is pulled in as a submodule
  (e.g. tampered release tags, a typosquat on the repo name).
- Accidental disclosure of secrets, credentials, or private data committed
  to the repository history.

Ordinary correctness bugs in a rule (wording, missing context, Dewey
misclassification) are not security issues — please open a regular GitHub
issue for those.

## Supported versions

This repository uses [DateVer](./workflows/datever.mdc). The `main` branch
is the only supported version. Consumers pin a specific commit via git
submodule; if you find an issue in an older tag, please still report
against `main` — we will note the affected range in the fix.

## Reporting a vulnerability

Email the maintainer privately:

- **[christopher@miniforge.ai](mailto:christopher@miniforge.ai)**

Please include a clear description, steps to reproduce, and the impact you
believe the issue has on consuming repositories. If you'd prefer encrypted
email, say so in an initial message and we'll exchange a public key.

**Please do not open a public GitHub issue for security reports.**

## Response expectations

- We aim to acknowledge reports within **3 business days**.
- We aim to provide an initial triage and plan within **10 business days**.
- Fixes will typically land on `main` within **30 days** of a validated
  report, with coordinated disclosure where appropriate.
- Standard coordinated disclosure window: **90 days** from the initial
  report, unless we and the reporter agree on a different timeline.

## Credit

Reporters who wish to be credited will be acknowledged in the commit message
of the fix and in the project's release notes. Reporters who prefer
anonymity will be honored.
