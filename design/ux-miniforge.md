# Miniforge UX Guidelines

Miniforge is infrastructure — a workflow runtime and fleet management platform targeting developers and platform engineers. Two surfaces:

1. **Miniforge TUI** — Cross-platform terminal interface. Primary day-to-day interaction surface.
2. **Miniforge native app** — macOS-first GUI for fleet oversight and configuration. Windows deferred.

General principles in `ux-general.md` apply everywhere. This document extends them for Miniforge-specific contexts.

---

## Audience

**Primary users are developers and platform engineers.** Design decisions that trade discoverability for density, speed, or power are acceptable here in ways they would not be in consumer products. That said:

- "It's for developers" is not a license for bad UX. Even expert users benefit from clear feedback, good error messages, and consistent patterns.
- Assume familiarity with terminals, CLI conventions, and developer tooling. Do not patronize with excessive wizard-style hand-holding.
- First-run experience still matters. A developer's first impression determines whether they invest in learning the tool.

---

## Miniforge TUI — Terminal Interface

### Cross-platform constraints

The TUI must function across macOS, Linux, and Windows (WSL and native terminal). Design for the lowest common denominator where appearance is concerned, but optimize for the rich case.

**Terminal capability tiers:**

| Tier | Capability | Target experience |
|---|---|---|
| **Dumb** | No color, no cursor control | Readable plain-text output; no layout features |
| **ANSI** | 16-color, basic cursor control | Color-coded status, simple boxes, spinners |
| **256-color / True-color** | Rich color palette | Full color ramp, nuanced status coding |
| **Kitty / modern** | Pixel graphics, font ligatures | Optional enhanced glyphs and icons |

Always detect capability; never assume. Respect `$NO_COLOR`, `$TERM=dumb`, and `$COLORTERM`.

### Layout & grid

- **80-column safe:** All primary content must be legible in an 80-column terminal. Do not rely on wider layouts for essential information.
- **120-column expanded:** Use additional width for secondary panels, progress details, or side-by-side comparisons when available.
- Use Unicode box-drawing characters (`─ │ ╭ ╰ ┬ ┼`) for structure when the terminal supports them. Fall back to ASCII (`-`, `|`, `+`) in dumb mode.
- Align tabular data using consistent column widths. Right-align numeric values. Left-align labels and strings.

### Visual hierarchy in text

Without pixels, hierarchy is expressed through:

1. **Position** — Most important information first in output order and leftmost in layout.
2. **Color** — Reserve bright/bold colors for status signals. Muted/dim for secondary info.
3. **Weight** — Bold for labels, headings, actionable items. Normal weight for values and descriptions.
4. **Whitespace** — Blank lines between logical groups. Consistent indentation for nesting.
5. **Box-drawing structure** — Panels and sections communicate grouping.

### Status and feedback

Every long-running operation must display progress. No silent waiting.

| Duration | Required output |
|---|---|
| < 300ms | Nothing. Result appears immediately. |
| 300ms – 2s | Animated spinner (`⠋⠙⠹⠸⠼⠴⠦⠧⠇⠏` or `|/-\`) with operation label |
| 2s – 10s | Spinner + elapsed time counter |
| 10s – 60s | Progress bar or step indicator if progress is determinable; elapsed time always shown |
| > 60s | Step-by-step checklist of completed/in-progress/pending stages; estimated remaining time if computable |

Use semantic colors for status:

- **Green (`✓`)** — Success, completed, healthy
- **Yellow (`⚠`)** — Warning, degraded, pending review
- **Red (`✗`)** — Error, failed, critical
- **Blue (`ℹ`)** — Informational, in-progress
- **Dim/gray** — Inactive, skipped, secondary

### Error messages

Miniforge errors must follow this format — no exceptions:

```
✗ [Error type]: Brief description of what failed
  Cause:  What caused the failure (if determinable)
  Fix:    Concrete next action the user can take
  Docs:   URL or command to get more detail (if applicable)
```

Never output a bare exception class or stack trace as the user-facing message. Log verbosity is controlled separately (`--verbose` / `MINIFORGE_LOG=debug`).

### Keyboard navigation

- Arrow keys navigate list selections where applicable.
- `q` / `Ctrl-C` / `Esc` exits any interactive pane.
- `?` or `h` opens contextual help in any interactive view.
- `Enter` confirms selection.
- Vim bindings (`j/k` for up/down, `g/G` for top/bottom) as secondary aliases in list views.
- Tab for focus traversal in multi-pane views.

All keybindings must be discoverable — shown in a persistent status line or triggered by `?`.

### Output philosophy

- **Machine-parseable by default when piped.** Detect `isatty()`. When output is piped, emit structured plain text or JSON (controlled by `--output json`). Never send ANSI codes to a pipe.
- **Verbose is opt-in.** Default output is signal, not noise. Progress details, debug logs, and secondary metadata require explicit flags.
- **Reproducible output.** Given the same inputs, the same output appears. Avoid timestamps or random IDs in primary output lines unless they are meaningful to the operation.

---

## Miniforge Native App — macOS GUI

### Follow Apple HIG strictly

This is a macOS-native application. Deviate from Apple HIG only with documented justification.

**Strictly follow:**
- Standard macOS window chrome (title bar, traffic lights, toolbar)
- Sidebar + detail layout for list/detail views (NSSplitViewController pattern)
- Menu bar with full keyboard-accessible menu hierarchy
- `Cmd+,` for Preferences
- `Cmd+W` to close; `Cmd+Q` to quit
- `Cmd+R` to refresh / reload
- System color tokens — never hard-code semantic colors

### Spacing

Use the Apple 8pt base grid:

- Base unit: **8pt**
- Half-step: **4pt** (tight contexts, icon padding)
- Common values: 8, 16, 24, 32, 40, 48pt
- Edge margins: **20pt** standard, **16pt** compact mode
- Touch/click targets: **44 × 44pt minimum** (even for small visual elements)

### Typography

- System font: **SF Pro** throughout. No custom typefaces.
- Body (sidebar items, list rows, form labels): **13pt regular**
- Secondary / captions: **11pt regular**, `secondaryLabel` color
- Headings (section headers, panel titles): **13pt semibold** or **15pt medium**
- Monospace (command output, paths, identifiers): **SF Mono 13pt**
- Apply `Dynamic Type` scaling support. Test at minimum and maximum accessibility sizes.
- Line length in text-heavy views: **50–75 characters per line** maximum.

### Color & appearance

- Use semantic system color tokens exclusively: `label`, `secondaryLabel`, `tertiaryLabel`, `systemBackground`, `secondarySystemBackground`, etc.
- Dark mode: first-class citizen, not an afterthought. Test every new view in both appearances.
- Accent color: match macOS system accent color preference unless a fixed brand color is required (prefer the system).
- Status colors: derive from system semantic colors (`systemGreen`, `systemYellow`, `systemRed`) to inherit dark/light mode adaptations.

### Animation

- Standard transition: **0.25–0.30s**, spring physics where appropriate.
- Maximum duration for any routine transition: **0.5s**.
- Respect `NSWorkspace.shared.accessibilityDisplayShouldReduceMotion`. Replace animations with instant transitions or cross-fades when enabled.
- No idle/looping animations in resting UI states.

### Developer-audience specific patterns

- **Density over discovery:** Fleet overviews and job lists should prioritize information density. Developers will learn the layout; they should not be forced into a simplified view.
- **Command palette:** Provide `Cmd+K` (or equivalent) for quick action search across all available commands. Reduces reliance on menu navigation for power users.
- **Log/output view:** Any view showing workflow or job output must use a monospaced font, support selection/copy, and handle ANSI color codes (strip or render, configurable).
- **Deep links:** Every fleet entity (job, workflow, run) must be addressable — copyable identifier visible in the UI.

### Error states in GUI

Match TUI error structure — translate to GUI affordances:

- **What failed:** Primary text in alert or inline error component (red).
- **Cause:** Secondary text below (if determinable).
- **Fix:** Button labeled with the specific action (e.g., "Reconnect", "Check Credentials", "View Logs") — not a generic "OK".
- **Docs:** Inline link to documentation page or `?` button.

Never use bare `NSAlert` with default "OK" as the only recovery path for an actionable error.

---

## First-Run & Onboarding

- **CLI:** `miniforge init` must complete in under 2 minutes for the common case. Emit clear progress through each stage. On completion, print the first meaningful action the user can take.
- **GUI:** First launch shows a setup checklist, not a blank state. Check connectivity, credentials, and fleet config. Each failed check shows the specific fix action.
- **Empty states are never blank.** A fleet with no jobs shows: what it is, why it's empty, and the exact command or action to add the first job.

---

*Cross-reference: `ux-general.md` for platform-agnostic principles. Apple Human Interface Guidelines for macOS-specific components. `ux-thesium.md` for the Thesium product layer.*
