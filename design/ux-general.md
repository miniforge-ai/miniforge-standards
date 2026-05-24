# General UX Guidelines

Platform-agnostic best practices. Apply these everywhere — macOS native, web, TUI, mobile.
Product-specific extensions live in `ux-miniforge.md` and `ux-thesium.md`.

---

## 1. Visual Hierarchy

Every screen has a single most-important thing. Everything else defers to it.

- **Position:** Higher = more important. Primary actions top or top-right; secondary below or de-emphasized.
- **Size & weight:** Larger and bolder draws the eye first. Reserve heavy weights for headings and primary CTAs.
- **Contrast:** High-contrast elements demand attention; low-contrast elements recede. Use contrast intentionally, not arbitrarily.
- **Maximum depth:** Three levels of visual hierarchy per screen is almost always enough. More than three creates confusion, not richness.

### Grouping & containment

Containers communicate relationships. A card or bordered group says "these things belong together and behave together." Use this:

- To show what is interactive vs. static (e.g. selectable items inside a container, disabled items outside it)
- To separate navigation from content from metadata
- To prevent visual noise from unrelated elements bleeding into each other

Do not add a container just for decoration. Every visual wrap should carry semantic meaning.

---

## 2. Spacing & Grid

Use the **4pt grid system** throughout. All spacing values are multiples of 4 (4, 8, 12, 16, 20, 24, 32, 40, 48…). This creates implicit rhythm and makes "does this look off?" answerable by math, not just eye.

- **Why 4pt:** Every value divides evenly in half, allowing fine adjustments without breaking the grid. Consistent with Apple's native spacing increments and Material Design's 4dp grid.
- **Column gaps:** 8px minimum between data columns. 16px preferred for readable density.
- **Breathing room:** Generous whitespace is not wasted space. Crowded layouts read as lower quality. When in doubt, add space.

### Padding rule

For interactive elements (buttons, inputs, cards):

> Horizontal padding ≥ 2× vertical padding

A button with 8px top/bottom padding should have ≥ 16px left/right padding. This produces natural-looking proportions and adequate tap/click targets.

---

## 3. Typography

Design is mostly text. Get typography right and everything else is detail.

### Font family

- **One font family per design.** Mixing two typefaces requires strong typographic skill to avoid conflict. Default to a single, well-designed sans-serif.
- Recommended starting points: Inter, SF Pro (macOS), Geist, IBM Plex Sans.
- Avoid decorative or display fonts for UI text. Reserve them for marketing/branding moments if at all.

### Size scale

- **Maximum 6 size steps** in the UI. More steps create noise, not hierarchy.
- **Body text baseline: 15–16px** (or pt equivalent). Below 14px degrades readability; above 16px wastes space in dense UIs.
- **Practical scale example:** 11 / 13 / 15 / 17 / 20 / 24. Each step reads clearly distinct from its neighbor.
- **Display/hero headings** (above 24px) are for marketing contexts, not dense application UIs. Keep UI headings at or below 24px unless there is a specific need.

### Polish adjustments

- **Letter-spacing:** Tighten by approximately 1–2% (`letter-spacing: -0.01em` to `-0.02em`) for a more refined, professional feel. Applies especially to headings and labels.
- **Line-height:** Body text: 1.4–1.6×. UI labels and dense data: 1.2–1.35×. Code: 1.5×.
- **Font weight hierarchy:** Use weight changes (400 → 500 → 600 → 700) to establish hierarchy before reaching for size increases.

---

## 4. Color

### Color ramp & semantics

Define a **semantic color vocabulary** before implementing any screen:

| Semantic role | Usage |
|---|---|
| `primary` | Brand-identified interactive color; CTAs, links, highlights |
| `surface` | Background of cards, panels, elevated content |
| `background` | Page/window base layer |
| `text-primary` | Main body text |
| `text-secondary` | Labels, captions, supporting text |
| `success` | Confirmations, completed states, positive deltas |
| `warning` | Caution states, degraded but not failed |
| `error` | Failures, destructive actions, validation errors |
| `info` | Neutral informational states |

**Let the color find you.** Don't force brand colors into semantic roles they don't fit. If the brand blue is too saturated for a subtle informational state, derive a tinted variant.

### Dark mode depth

- Create depth through **surface layering**, not shadows.
- Card backgrounds should be **lighter than** the window background (e.g. `#1C1C1E` card on `#000000` background).
- Dim saturation for dark mode variants of accent colors. Fully-saturated colors vibrate against dark backgrounds.
- When reversing a light-mode design: flip contrast ratios, don't just invert hex values.

### Light mode depth

- **Shadows** are the primary elevation signal in light mode.
- Elevation layers: 0dp (base) → subtle shadow → medium shadow → prominent shadow.
- If the shadow is the first thing a reviewer notices on a component, it is too strong. Shadows should be felt, not seen.
- Rule of thumb: content that floats above other content (modals, dropdowns, tooltips) requires proportionally more shadow.

---

## 5. Component States

Every interactive element must define **all five states** before shipping. Missing states produce broken-looking UIs.

| State | When it appears |
|---|---|
| **Default** | At rest, not yet interacted with |
| **Hover** | Pointer over the element (desktop only) |
| **Active** | Mouse/finger down; element is being pressed |
| **Selected / Focus** | Keyboard focus ring or toggled-on state |
| **Disabled** | Action is unavailable; element is present but inert |

Disabled states: reduce opacity (40–50%) **or** reduce contrast — not both, which becomes invisible. Always pair with a tooltip or adjacent explanation of *why* disabled if the reason is non-obvious.

---

## 6. Feedback & Response Times

Based on Nielsen's response time research and Henty (2017), "UI Response Times":

### Time thresholds

| Latency | Perception | Required feedback |
|---|---|---|
| **< 100ms** | Instantaneous. Action feels directly causal. | None. The result *is* the feedback. |
| **100ms – 1s** | Slight delay. User stays in flow. | None required, but hover/active states help. |
| **1s – 2s** | Noticeable. User aware of wait. | Simple spinner acceptable. |
| **2s – 5s** | Patience required. | Loading spinner with optional label. |
| **5s – 10s** | **Worst zone.** Long enough to break focus; too short to switch tasks. User frustration peaks here. | Percent-done progress indicator. **Strongly prefer redesigning to avoid this range.** |
| **10s – 1min** | User abandons focus on task. | Step-by-step progress, checklist, or percent-done. Allow background. |
| **1min – 10min** | User does other work. | Notifications on completion. Status display available on demand. |
| **> 10min** | Full async job. | Background task with notification. Consider reminders for > 1hr. |

### Feedback patterns

- **Loading spinners:** Indeterminate operations of 1–5s duration.
- **Progress bars:** Determinate operations where completion percentage is knowable.
- **Skeleton screens:** Preferred over spinners for content areas that have known layout (cards, lists, dashboards). Reduces perceived wait time.
- **Success messages:** Always confirm completion of user-initiated write operations (save, submit, send, delete). Transient toast/banner for non-destructive; modal or inline for destructive.
- **Error messages:** Actionable. State what failed, why (if determinable), and what to do next. Never just "An error occurred."

---

## 7. Microinteractions

Microinteractions bridge action and confirmation. Every user-initiated action should have a perceptible response.

### Principles

- **Cause and effect must be legible:** The animation or state change should clearly communicate what happened, not just that *something* happened.
- **Duration:** 150–300ms for most UI transitions. Under 100ms feels glitchy. Over 400ms feels sluggish.
- **Easing:** Ease-out for elements entering the screen (fast start, slow finish). Ease-in for elements leaving. Ease-in-out for repositioning.

### Common patterns

| Action | Microinteraction |
|---|---|
| Copy to clipboard | Brief slide-up chip with green checkmark, fades back to button in ~1.5s |
| Form submission success | Input border transitions to `success` color; icon appears |
| Destructive confirm | Button transitions to confirmation state (e.g. "Delete" → "Are you sure?") |
| Toggle/checkbox | Smooth fill or slide, not instant snap |
| Loading completion | Spinner resolves to checkmark before disappearing |

### Overlays

- Use **progressive blur** (backdrop-filter: blur) behind modals and sheets for context preservation.
- Glassmorphism (frosted glass surfaces): effective for depth signaling in modern UIs; overuse flattens the effect. Reserve for top-level overlays and prominent floating panels.

---

## 8. Nielsen's 10 Usability Heuristics

These are validation criteria, not design instructions. Use them to audit finished work.

1. **Visibility of system status** — Always inform users what is happening, within reasonable time.
2. **Match between system and the real world** — Speak the user's language; use familiar concepts and metaphors.
3. **User control and freedom** — Support undo and redo. Provide clear escape routes from unwanted states.
4. **Consistency and standards** — Same action always produces the same result. Follow platform conventions.
5. **Error prevention** — Design out errors before they happen. Confirm before destructive actions.
6. **Recognition over recall** — Make options visible. Don't require users to remember information across contexts.
7. **Flexibility and efficiency of use** — Expert shortcuts alongside novice paths. Power users must not be slowed by beginner UX.
8. **Aesthetic and minimalist design** — Every element earns its place. Irrelevant information competes with relevant information.
9. **Help users recognize, diagnose, and recover from errors** — Error messages in plain language, with actionable next steps.
10. **Help and documentation** — Contextual, searchable, task-focused. Never a wall of text.

---

## 9. Accessibility Baseline

These are non-negotiable minimums, not optional polish.

- **Contrast:** ≥ 4.5:1 for body text (WCAG AA). ≥ 3:1 for large text (18pt+ or 14pt bold).
- **Touch/click targets:** ≥ 44×44pt on touch surfaces (Apple HIG). ≥ 24×24px on desktop (WCAG 2.1 minimum; 44×44 preferred).
- **Keyboard navigation:** All interactive elements reachable and operable by keyboard alone.
- **Focus indicators:** Visible focus ring on all focusable elements. Never `outline: none` without a custom replacement.
- **Screen reader labels:** Every interactive element has a descriptive accessible label. Icon-only buttons require `aria-label` or equivalent.
- **Motion sensitivity:** Respect `prefers-reduced-motion`. Animations are enhancement, not requirement.
- **Color alone is never the only signal:** Pair color-coded states (error red, success green) with icons or text.

---

## 10. Design Trends: Current Context (2026)

Use trends as tools, not mandates. Apply when they serve the content; abandon when they fight it.

| Trend | When it works | When to skip |
|---|---|---|
| **Glassmorphism 2.0** | Top-level overlays, floating panels, premium/modern product feel | Dense data tables, small UI chrome, low-power contexts |
| **Archival Index** | Information-dense views with many data types; research/reference interfaces | Simple task-focused flows |
| **Microinteractions** | Any user-facing action with a meaningful result | Background/system operations the user didn't trigger |

---

*Sources: Kole Jain, "Every UI/UX Concept Explained in Under 10 Minutes"; Scott Henty, "UI Response Times" (2017); Nielsen Norman Group, "10 Usability Heuristics"; Apple Human Interface Guidelines.*
