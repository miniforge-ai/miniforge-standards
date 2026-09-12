# Thesium UX Guidelines

> **Private repository.** Thesium product-specific UX guidelines are maintained in
> [`miniforge-ai/thesium-standards`](https://github.com/miniforge-ai/thesium-standards)
> (private). See `design/ux-thesium.md` there.

## What lives there

- Thesium Financial: data source attribution, confidence indicators, delta color rules, skeleton loading patterns, Pro tier differentiation
- Thesium Career: GrowthRubric / EvidencePortfolio / LensApplication UX, Connect-style review semantics, projection engine traceability
- Shared Thesium visual identity patterns

## What lives here (public, in this repo)

Platform-layer rules that apply to all macOS-native Miniforge products, including Thesium:

- [`ux-general.md`](ux-general.md) — Universal UX best practices, response-time thresholds, Nielsen heuristics
- [`ux-miniforge.md`](ux-miniforge.md) — macOS native app rules (8pt grid, SF Pro, Apple HIG, animation timing), TUI cross-platform rules

## Agent fallback

If `miniforge-ai/thesium-standards` is not accessible in your execution context
(e.g. you are running in the public `miniforge-standards` repo without the
private submodule checked out), **do not proceed without a rule set**.
Use the two public files above as your working rules for Thesium UI work:

1. Load `ux-general.md` — applies universally and is never skipped.
2. Load `ux-miniforge.md` — applies to all macOS-native Thesium surfaces.
3. Note in your PR description that Thesium-specific visual identity rules
   (confidence indicators, delta colour system, data-source attribution)
   were not validated against the private standard, and flag for review by
   someone who has access to `miniforge-ai/thesium-standards`.
