# fix: stratified design is SICP, not Clean Architecture

## Overview

`foundations/stratified-design.mdc` (001) was titled "Stratified Design" but its
canonical strata —

```
0 Foundations → 1 Domain → 2 Application → 3 Adapters → 4 Infrastructure
```

— are **Clean Architecture / Hexagonal / Ports-and-Adapters** (Martin, Cockburn).
That is a different idea from stratified design (SICP §2.2.4, Abelson & Sussman),
and structurally it is a **linear chain**, not the branching **DAG of
abstraction** stratified design actually describes. The rule imported the *name*
and filled it with the *wrong substance* — and because 001 is `alwaysApply: true`,
every agent faithfully reproduced the confusion (the `Layer N` cargo-culting
across the codebases traces back here).

## Changes

- **`foundations/stratified-design.mdc` (001) — rewritten to real SICP.** A system
  is a sequence of layers, each a small *language* built on the one below; one
  layer's constructs are the next layer's primitives. A one-way DAG of
  abstraction that **branches** (lower constructs shared by many above). Strata
  are **discovered per problem**, not a fixed universal stack. Picture-language
  worked shape; robustness/localized-change as the payoff; anti-patterns name the
  exact failure (calling the five-name stack "stratified design"; decorative
  `Layer N` labels the code crosses; skip-level reaches; cycles).
- **`foundations/layered-architecture.mdc` (011) — new.** The Clean-Architecture /
  Dependency-Rule content moved here and named correctly: the inward-only module
  dependency stack, pure core, ports & adapters, boundary DTOs, enforcement hooks.
  Framed explicitly as *one coarse, module-level instance* of 001 — and as a chain
  by deliberate coarsening, with the finer branching strata living within its
  bands.
- **`languages/clojure.mdc` (210) — reframed.** The per-file `Layer N` convention
  is now stated as the per-file instance of 001: the in-file DAG may branch, the
  heading must be *true* (extract work that a higher band inlines), and "max 3" is
  a split heuristic, not the shape of the idea.
- **Indexes** (`index.mdc`, `CLAUDE.md`, `agents.md`, `README.md`) — add 011,
  reword 001, and split the conflated "Core Principles → Stratified Design" block
  into Stratified Design (the discipline) + Layered Architecture (the Dependency
  Rule).

## Why it matters

001 is the most foundational, always-injected rule. With the wrong concept
encoded, "compiling it to policy" enforces a five-layer chain — not stratified
design — and the prompt injection teaches every agent the cargo-cult form. Fixing
the source rule is the root-cause fix for the downstream layering drift; the
remediation of existing `Layer N` usage (lint for decorative bands, opportunistic
re-stratification) is a follow-up that now has a correct definition to check
against.

## Deployment

Doc/rule-only. Consuming repos pick it up on their next `.standards` submodule
bump. No code, no migration.

## Related

- SICP §2.2.4 "Example: A Picture Language" → "Stratified Design".
- Triggered by a per-file instance found in review (a Layer-2 entrypoint inlining
  Layer-1 work) — the symptom whose root cause is this rule.

## Checklist

- [x] New rule follows `meta/rule-format` (frontmatter, dewey 011, alwaysApply).
- [x] Index + CLAUDE + agents + README updated; cross-refs consistent (001 ↔ 011 ↔ 210).
- [x] No remaining "Stratified Design = Adapters→…→Foundations" conflation.
- [x] PR doc (721).
