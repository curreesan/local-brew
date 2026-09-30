# Ultimate Guide — Build Spec

This file is self-contained: whoever (or whatever) authors any page of "The Ultimate Guide"
should be able to read ONLY this file and correctly build or edit that page — including a fresh
agent with no memory of the conversation this file came from. Read it in full before authoring
or editing ANY page. If a page doesn't satisfy every applicable rule below, it is not done yet.

## What this guide is, and isn't

A from-scratch, standalone reference for Local Brew (`ree.selfcode.localbrew`) — written as if
it's the ONLY resource the reader has. Assume the reader has forgotten any prior Android course
content; teach as if genuinely new.

**Zero assumed familiarity, even where the reader has been hands-on.** The reader has been
building Local Brew alongside Claude all session and understands the app itself well — but that
is exactly why it's tempting to under-explain a construct on the theory that "they've basically
seen this already." Don't do that. Real feedback from this guide's own history: after reading
Part 1, the reader said they understood most of it "because I already work on the app," and
asked for it to be explained as if genuinely new to the material anyway — then pointed at a
specific spot where a shortcut had crept in (`data class`'s auto-generated functions were named
but not each individually explained). Treat every construct as the reader's first exposure to
it, full stop. In particular: **never gloss a set of auto-generated or implicit behaviors as a
group** ("it automatically gives you some useful functions") — enumerate each one by name,
explain what it actually does, why it exists, and give it its own example and scenario. A list of
named-but-unexplained functions is not a first-time explanation, no matter how short the list is.

**First-time examples are invented, not from the app — the app comes later.** When a language
construct or concept is taught for the first time (Parts 1-2 especially), the example must be a
small, from-scratch, made-up scenario — a `Person` data class, a `Shape` sealed class, a
`Greeter` object — never Local Brew's own code. Save the app's real code for when the guide
actually reaches that file or screen (Part 3 onward, or a screen-by-screen section) — at that
point, the construct has already been taught cleanly once, and the app's version becomes "see,
here's this same thing doing real work," not the reader's first encounter with the idea. The one
exception is Part 1's *general Android/app-dev concepts* subsection (SDK, Gradle, the Manifest,
`compileSdk`/`minSdk`/`targetSdk`) — those are inherently about this project's actual setup, so
they stay tied to Local Brew's real config files; the generic-first rule applies to *language*
constructs and *API* mechanics, not to project-configuration topics that don't have a meaningful
generic form. When in doubt, this is the version of the rule that already worked well in Part 2:
`remember`/`rememberSaveable` were taught first with a plain, invented `Counter()` composable,
and only afterward reinforced with the real `MainScreen.kt` bug as a second, real-world scenario.

It is NOT the build-log artifact
(https://claude.ai/code/artifact/0d81115c-4b1f-45d8-9b23-bcfd9ec5c539) — that one stays a
chronological diary of the real back-and-forth (bugs, wrong turns, corrections) and is never
edited to match this guide. This guide is the clean, current-state teaching reference: no "you
asked" callouts, no dead ends, just the concepts and the app explained properly, in the best
order to learn them, using the real current code.

## Structure: hub + one Artifact per Part

"The Ultimate Guide" is a hub Artifact — an index page with one card/link per Part, each a
plain `<a href>` to a **separately published Artifact** (its own real URL). Not tabs, not
iframes, not a single-page app faking navigation — genuine separate pages, each independently
updatable later without touching the others.

Every page (hub included) gets the same navigation chrome so the set reads as one book:
- A breadcrumb/header: "Part N of 7 — <title>", a link back to the hub, and prev/next links to
  the adjacent Parts.
- Prev/next links only go live once the target page is actually published — see "Build order
  vs. reading order" below. Until then, that Part's hub card is visibly marked "not yet
  published" rather than linking to a 404.

## Part order (do not resequence without the user's sign-off)

0. Orientation
1. General Android/app-dev concepts + Kotlin fundamentals for a web dev
2. Compose fundamentals: composables, state, recomposition
3. Backend & data layer: Retrofit, Firebase Auth, Firestore, Room
4. Navigation & the back stack
5. Screen-by-screen build-along (FOUR separate sub-pages — see "Part 5" section below):
   5.1 Auth (Signup + Login)
   5.2 Main shell (bottom nav, drawer, the location-permission state machine)
   5.3 Discover + Detail
   5.4 Search + Favorites + Profile
6. End-to-end system design (one full click-trace across every layer)
7. Theming & custom icons
8. Appendix — Must-know Android concepts beyond this app

Nav chrome consequence: prev/next links chain 4 → 5.1 → 5.2 → 5.3 → 5.4 → 6. The hub's card for
"Part 5" should itself link to 5.1 (the entry point), with 5.1's own page linking onward to 5.2,
etc. — the hub does not need four separate cards, one is enough, exactly like every other Part.

## Part 1's two subsections

Part 1 covers two genuinely different things, in this order:

1. **General Android/app-dev concepts (app-scoped, in detail).** The absolute platform
   vocabulary a total beginner needs before anything else makes sense — pitched at "what even is
   an SDK" — but taught in DETAIL and always tied to something real in Local Brew, not defined in
   the abstract. Cover at minimum: SDK vs. JDK/JVM, APK vs. AAB, what Gradle actually is and does
   (tie to this project's real `libs.versions.toml`/`build.gradle.kts`), `compileSdk`/`minSdk`/
   `targetSdk` (cite this project's actual values — `minSdk = 24`, `targetSdk = 37` — and what
   each number actually constrains), `AndroidManifest.xml`'s job, `Application` vs. `Activity` vs.
   `Context` (tie to `LocalBrewApp.kt`), build variants (debug vs. release), ProGuard/R8 at a
   light conceptual level, and ADB/Logcat as the basic tools used throughout this project's real
   debugging history (the emulator/ADB incidents already logged in the build-log artifact are fair
   game to reference here as real examples). Sourced by looking at
   https://github.com/amitshekhariitbhu/android-interview-questions for scope calibration, but
   curated down hard — that repo lists 200+ topics; only cover what's actually load-bearing for
   understanding Local Brew's own codebase here. Broader industry topics not used in this app
   belong in Part 8, not here.
2. **Kotlin language fundamentals for a web dev** (as originally scoped): `val`/`var`, data
   classes, null safety, sealed classes, coroutines vs. `async`/`await`, etc. — see the original
   Part 1 description below.

## Part 8 — Appendix: must-know Android concepts beyond this app

A separate, clearly-labeled appendix, NOT part of the main "teach Local Brew" narrative — kept
out of the Part 0-7 story so the core guide stays focused on this one app. Curated from
https://github.com/amitshekhariitbhu/android-interview-questions, but deliberately NOT
exhaustive: pick genuinely must-know, commonly-asked fundamentals a working Android dev or
interview candidate should recognize, not full coverage of that repo's 200+ topics (skip deep
RxJava operator lists, NDK, multi-module architecture, and "design Instagram/Uber/WhatsApp"
system-design prompts — diminishing returns for a "must-know basics" appendix).

Do not re-teach anything Parts 0-7 already cover — link back to it instead (e.g. `remember`,
`sealed class`, coroutines basics, Room, Retrofit are already owned by earlier pages). This
appendix's job is to fill in the well-known Android concepts that never came up naturally while
building Local Brew specifically, because this one app happens not to use them. A working
starting list (confirm/adjust once actually drafting this page, but don't balloon past it without
checking in):

- The four Android app components: Activity, Service, BroadcastReceiver, ContentProvider (Local
  Brew only really exercises Activity — the other three are must-know even though unused here)
- ~~Activity lifecycle callbacks~~ — moved to Part 4 (see "Android system lifecycle (mandatory
  Part 4 section)" below); this appendix should only link back to Part 4's canonical explanation,
  not re-teach it.
- Fragment basics and lifecycle (Local Brew is 100% Compose, no Fragments — explain what a
  Fragment is and why many newer apps, including this one, skip them entirely)
- Context: what it actually is, Activity Context vs. Application Context, common leak pattern
  from holding the wrong one
- Views/ViewGroup and RecyclerView basics, explicitly contrasted with Compose's `LazyColumn`
  (which Local Brew actually uses) as the modern replacement
- Intents: explicit vs. implicit, and how they differ from this app's Navigation-Compose-based
  in-app navigation (already taught in Part 4)
- Memory leaks vs. OutOfMemoryError, and ANR (Application Not Responding) — what causes one
- Threading fundamentals beyond coroutines: `Looper`/`Handler`, `Thread` vs. coroutines
- Dependency injection as a general pattern (Dagger/Hilt), contrasted with this project's manual
  `Graph` object DI (already taught in Part 3) — why a bigger app might reach for a DI framework
  instead
- Common architecture patterns: MVC vs. MVP vs. MVVM vs. MVI, and where this app's own
  ViewModel-based structure fits
- Testing basics: unit vs. instrumented tests, what Espresso/Robolectric/Mockito are for
- Common Kotlin interview staples not already covered elsewhere in this guide: `lateinit` vs.
  `lazy`, scope functions (`apply`/`let`/`run`/`with`/`also`), `equals()`/`hashCode()` contract

## Part 5 — Screen-by-screen build-along (revised structure, decided directly with the user)

This is NOT a tour of already-finished files. Direct user instruction, verbatim: "The idea for
part 5 for me was for me, by end to end, i meant as if youre teaching a non android developer from
scratch how to build this app. Think of this as a lets build the app from scratch while teaching
everything from scratch, all the concepts, topics, classes, methods, functions, system design
etc." Frame every sub-page as an active build narrative — as if a non-Android developer is sitting
down with an empty project and constructing this screen, and the moment some new class, method, or
concept is actually NEEDED to solve the problem in front of them, that's the moment it gets taught.
Not "here's the finished ViewModel, here's what it does" — "we need to hold this text field's
value somewhere that survives recomposition... here's why... here's the tool for that."

**Consequence this reframing has that must not be missed:** Parts 1-4 taught Kotlin, Compose
fundamentals (state/recomposition/`remember`), the backend/MVVM shape, and navigation — but never
had reason to teach the actual UI-building vocabulary a real screen needs: `TextField`/form state
and validation, `LazyColumn`, `AlertDialog`/`Snackbar`, `Modifier.clickable` and other modifier
chains, keyboard/focus handling, `Scaffold` specifics, `ModalNavigationDrawer`. Any such construct
that has NOT already been canonically taught elsewhere (check the Status Tracking table) needs the
full first-time treatment used everywhere else in this guide: generic invented example first (a
bare `TextField` in a throwaway composable, say), THEN applied to Local Brew's real screen. Do not
treat these as "just applying stuff now" — for many of them, Part 5 is their first appearance
anywhere in the guide.

**The single most important requirement, direct user instruction, verbatim:** "The most important
part about this section is going file by file, line by line fro each code and explaining what they
do, which library do they come from, is it a class/instance, what are its methods, what do they
take as arguments, what does it return, stuff like that." This sharpens the existing "per-file
template"'s step 4 ("Code + syntax walkthrough") into a hard, non-negotiable mandate for every file
touched anywhere in Part 5: **every significant line/call must be individually attributable** —
which package/library it comes from, whether it's a class, an interface, a top-level function, or
an instance/object, its real method signatures (every parameter + its type + what it means), and
its real return type. This is the same bar Part 3's Retrofit/Room cards and Part 4's Navigation
Compose cards were already held to (and eventually met, after rebuilds) — apply it by default in
Part 5 from the start, file by file, for every screen, not just once per library on first
appearance elsewhere already covered.

**Structure: four separate sub-pages**, each its own published Artifact, same cross-linking
pattern as every other Part (see "Structure: hub + one Artifact per Part" above):
- **5.1 — Auth** (Signup + Login): first real screens built, since the nav graph needs a start
  destination. Introduces `TextField`/form state/validation and reinforces Firebase Auth from
  Part 3.
- **5.2 — Main shell**: `MainScreen.kt` — bottom nav, drawer, and the location-permission state
  machine this session spent a great deal of time on (the counter-based state derivation and the
  resume-recheck fix are both real, current code by the time this page is written — re-read
  `MainScreen.kt` fresh, don't rely on conversation memory of it). Reinforces Part 4's navigation
  material immediately after it's taught.
- **5.3 — Discover + Detail**: the primary café list and its detail screen, reached via
  navigation from Discover. Introduces `LazyColumn`, reinforces Retrofit/Room from Part 3 and
  `SavedStateHandle`/nav-argument passing from Part 4.
- **5.4 — Search + Favorites + Profile**: reinforcement-heavy, should move faster since most
  patterns by this point repeat what 5.1-5.3 already taught; reinforces Firestore from Part 3.

**Build order is dependency order, not file-tree order** — decided directly with the user: Auth →
Main shell → Discover → Detail → Search → Favorites → Profile. This is the order the app would
actually have to be built in for it to run at each intermediate step (you cannot meaningfully test
Discover before there's a way to log in and reach it).

**Per-screen shape within each sub-page** (adapt, don't abandon, the per-file template above):
1. No-code: what this screen does for the user, and any real design decision behind it.
2. Build the UI first, narrated as construction, teaching each new Compose building block the
   moment it's needed (generic example first if it's a true first appearance).
3. Wire the ViewModel — state shape + real functions, reinforcing Part 3's MVVM vocabulary.
4. Wire it into navigation — reinforcing Part 4's vocabulary.
5. File-by-file, line-by-line attribution per the mandate above, for every file this screen
   touches.

Do not begin building any of these four sub-pages until this section has the user's sign-off on
the plan as a whole — this was explicitly requested before implementation: "I feel like its better
to discuss the structure first and then create the artifact."

## Teaching a library or framework's API for the first time (mandatory three-pass structure)

This is a standing rule, not a one-off fix for a single Part — it applies to Part 3 (Retrofit,
Room, Firebase Auth, Firestore) first, and to Part 4 (Navigation Compose's `NavController`/
`NavHost`/`SavedStateHandle` API) as well, and to any future Part that introduces a
library/framework's API surface for the first time. It came directly from real user feedback on
Part 3: the first attempt skipped straight into this app's specific files, so the reader never
got a clean picture of the technology itself, and never saw the library's actual functions
documented with what they take and return. Three distinct passes, in this order, and do not
collapse or skip one:

**Pass 1 — No-code architecture overview.** Before any code, before any specific library
function: a short, purely conceptual map of the pieces and what each is FOR. No function
signatures, no code samples. For Part 3 this is: `Graph` introduced as a singleton `object` where
everything is declared `by lazy`, then every Repository named one by one with just its job
("`AuthRepository` handles login/signup," "`CafeRepository` is the front door for café data,"
"`FirestoreRepository` handles saved favorites"). For Part 4 this is: what destinations actually
exist in this app (from `Screen.kt`) and what the overall navigation graph's shape is, before any
`NavController` function is shown. The reader should be able to picture the whole shape with
their eyes closed before seeing a single line of code. Keep this pass genuinely short — it's a
map, not a lesson.

**Pass 2 — The library's own API, documented like real API reference material, with real
input/output.** This is the pass that was missing entirely in the first draft of Part 3, and is
the heart of this rule. For each library/framework being introduced (Retrofit, Room, Firebase
Auth, Firestore in Part 3; Navigation Compose's `NavController`/`NavHost`/`composable()`/
`SavedStateHandle` in Part 4), cover: what problem it solves conceptually, the Gradle dependency
that brings it in, and its real API surface — the actual classes/interfaces/annotations/functions
it exposes. For every significant function: what parameters it takes, what it returns, shown with
a representative code example **and the actual shape of the output** (e.g. "here's a Retrofit
interface method, here's literally the object/JSON that comes back"; "here's `NavController
.navigate(route)`, here's what happens to the back stack afterward"; "here's a Room DAO `suspend
fun`, here's the `List<Cafe>` it hands you"). The example code in this pass does NOT need to be
avoided just because it resembles real usage — a library's API doesn't have a meaningful
"generic form" separate from showing how you'd call it, so a small, honestly-representative
example of the library's own usage pattern is correct here (this is different from a *language
construct* like `data class`, where a fully invented, unrelated example like `Point` is the right
call — see the generic-first rule above, which still applies to language constructs and does not
change). What Pass 2 must NOT do is reach for this app's actual files yet (`GeoapifyApiService`,
`NavGraph.kt`) — those come next.

**Pass 3 — The real per-file/per-usage application.** Now, and only now, walk through this app's
actual files applying everything Pass 2 just documented. This pass should move noticeably faster
than Pass 2 — it is reinforcement ("and here's Local Brew's actual interface, using exactly the
pattern you just learned, with these specific real parameters"), not a second first-exposure. Do
not re-explain what `suspend`, `@Query`, or `NavController.navigate` do here — just apply them.
This is also where any real bugs/decisions from this project's history belong (e.g. Part 3's
Retrofit-default-value gotcha, Part 4's two real navigation bugs).

Self-check before publishing any page this rule applies to: if you removed Pass 3 entirely, would
Pass 1 + Pass 2 alone still teach a reader everything about the technology itself, just without
this app's specific files? If not, Pass 2 wasn't actually complete.

**Part 4's Pass 2 failed this self-check on its first attempt — real, direct user feedback,
verbatim:** "for the navigation, you got into the cafe app navigation immediately. There was no
teaching of the actual library classes, methods, that provides. what happens when u call it, does
it create an object or a class, what does each instance's method take as an argument, what does it
return, how do you use it. where does navbackstackentry come from, what is it exactly, what is
savedInstanceState, where does it come from, what is it exactly, none of it was explained in
depth." Part 3's Pass 2 (Retrofit/Room/Firebase Auth/Firestore) already met the user's bar and was
NOT re-litigated — treat it as the concrete calibration example for exactly how much depth "real
API reference material" means. Match that depth, not the first draft of Part 4.

For Navigation Compose specifically, Pass 2 must give EACH of the following its own explicit
treatment — a checklist, not a suggestion. For every item, answer all four questions by name,
don't just imply the answers through an example:
1. **Is it a class, an interface, or a function?** (e.g. `NavController` is a class; `NavHost` and
   `composable()` are composable functions; `rememberNavController()` is a function that returns a
   `NavController`.)
2. **How do you actually get an instance of it?** — never assume this is obvious. `NavController`
   is never constructed with `NavController()` directly in app code; it comes from calling
   `rememberNavController()`. `NavBackStackEntry` is never constructed by app code either — it is
   *handed to you* as the parameter of `composable(route) { entry -> ... }`'s trailing lambda, one
   fresh instance per screen currently on the stack.
3. **Real signature: every parameter (name, type, meaning) and the real return type.**
4. **A representative example AND its real, concrete resulting behavior** — for a `NavController`
   method, don't just show the call; show the back stack as an actual list before and after (e.g.
   `["Main"]` → call `navigate("detail")` → `["Main", "detail"]`).

Required items for this checklist, at minimum: `NavController` (the class itself),
`rememberNavController()`, `NavHost` (its `navController`/`startDestination`/builder-lambda
parameters), `composable()` (its `route` parameter and its content lambda's parameter), `navigate()`
(parameters, return type `Unit`, the back-stack side effect), `popBackStack()` (parameters, `Boolean`
return, the back-stack side effect), **`NavBackStackEntry`** (what it actually IS — one specific
entry's own arguments/lifecycle/saved-state, scoped to exactly that entry's time on the stack, not
a global object), and **`SavedStateHandle`** (where it comes from — `NavBackStackEntry.savedStateHandle`
— what it actually is: a `Bundle`-backed key/value store that survives process death, tied to that
one back stack entry's lifetime; its real methods: `set(key, value)`, `get<T>(key)`,
`getStateFlow(key, default)`). `NavBackStackEntry` and `SavedStateHandle` were named explicitly by
the user as completely unexplained in the first draft — each needs a full paragraph plus a worked
example, not a passing mention folded into something else.

Use a fully generic, invented example app (per the generic-first rule for API mechanics) to teach
this checklist — e.g. a tiny two-screen notes app — completely before Pass 3 ever opens
`NavGraph.kt`.

## MVVM architecture pattern (mandatory Part 3 section)

Part 3 must explicitly teach MVVM as a named architecture pattern, not just document Retrofit/
Room/Firebase/Firestore as isolated libraries — direct user instruction: "You have to not only
explain about the 3 db/apis but also explain the code pattern of the MVVM Architecture etc."
Calibration source: `Android_Fully_Explained.pdf` (Desktop), Day 8's Counter App chapter, which
teaches MVVM via a plain View/ViewModel/Model-Repository table mapped to React equivalents — use
that same table shape, but populate it entirely with Local Brew's own real classes, never the
Counter app's. Day 14's Wishlist chapter `Graph` singleton is also directly relevant calibration
for how deep the `Graph.kt` "why does this exist" explanation should go (expensive shared
resource, built once, handed out without threading `Context` through every constructor) — Part 3
already covers `Graph.kt`'s timing; this section is about naming the *pattern* the whole app's
layering follows, not re-explaining `Graph.kt` itself.

Placement: its own subsection, positioned after Pass 1 (repositories are already named there) and
before Pass 2 (before diving into any single library's API). This is the "zoom out and name the
whole shape" moment, not a fourth pass.

Required content:
- Name and define each layer: **Model** (the plain data — `Cafe`, `CafeDto`, the Firestore
  documents), **View** (the Composable screens — no business logic, no long-lived state of its
  own), **ViewModel** (holds current UI state + the logic for how it changes; survives
  configuration changes; the View reads from it and calls its functions, never the reverse), and
  **Repository** (`AuthRepository`/`CafeRepository`/`FirestoreRepository` — the single point of
  contact between a ViewModel and the actual data sources). Be explicit that "Repository" is a
  layer between ViewModel and Model that MVVM as originally named doesn't separately name — call
  out that this is a common, standard refinement of classic MVVM, not a deviation from it.
- A table: Layer | Local Brew's real class(es) | Its job | closest React-world equivalent (a
  Composable ≈ a presentational function component; a ViewModel ≈ a custom hook or a small
  store; a Repository ≈ an API-client module; reuse the "Web-dev analogies" rule). Use the app's
  real ViewModel files as the anchor: `AuthViewModel`, `DiscoverViewModel`, `SearchViewModel`,
  `DetailViewModel`, `FavoritesViewModel`, `ProfileViewModel` — re-read whichever ones the page
  actually references from disk first, per the standing re-read rule.
- The dependency-direction rule, stated explicitly and shown as a simple diagram: View → ViewModel
  → Repository → (Retrofit/Room/Firestore), one-way only. The View never calls a Repository
  directly; a Repository never reaches back up to touch a ViewModel or a View.
- The "why separate at all" reasoning (from the PDF's own framing, adapted): the View can be
  redesigned/redrawn without touching any logic; the ViewModel's logic can be reasoned about (and
  tested) without a screen on screen at all; the data layer (swap Retrofit for a different API,
  add a new cache) can change without the ViewModel or View noticing, as long as the Repository's
  function signatures stay the same.
- An Anticipated Questions box per the "First-time depth" rule — expect at least "isn't this just
  extra layers for no reason on a small app?" and "where does Compose's own state (`remember`)
  fit into this if the ViewModel already holds state?" (answer: `remember` is View-local,
  disposable UI state — like an expanded/collapsed toggle — that never needs to survive the
  screen being torn down; ViewModel state is the screen's actual data and does survive it).

Do not teach MVC/MVP/MVI here or contrast them here — that comparison is scoped to Part 8's
appendix (already listed there), since Local Brew only implements MVVM and Part 3 should stay
about this app's own pattern, not a survey of alternatives.

## Additional Part 3 gaps (added after a direct user request to identify what's missing)

Identified by re-reading the real `CafeRepository.kt` against what a working Android dev needs
beyond what Parts 1-3 already cover. These are real gaps in the app's own code, not invented
topics — teach them as honest observations about Local Brew's current design, including its
limitations, not as if the app already does the more complete version:

- **Error/loading state as a type, not just a `try/catch`.** `CafeRepository.getNearbyCafes`
  currently swallows a network failure into a fallback return value (`CafeFetchResult(isFromCache
  = true)`) rather than exposing a `Loading`/`Success`/`Error` shape the UI can react to
  explicitly. Teach this as the single most common gap between a demo app and a production one:
  introduce a small invented `sealed class UiState<T>` (`Loading`, `Success(data)`,
  `Error(message)`) as a first-time, generic construct (per the generic-first rule), then show
  concretely how Local Brew's real `CafeFetchResult`/`try-catch` pattern is one narrower way of
  handling the same problem, and what a `UiState`-based version would look like for the same
  function.
- **"Cache as fallback" vs. real offline-first, contrasted honestly.** Local Brew's actual pattern
  is network-first with Room read only as a fallback on failure (re-read `CafeRepository.kt`'s
  real `catch` block for the exact shape). Contrast this explicitly with the more standard
  production pattern — Room as the single source of truth the UI observes via a `Flow`, with the
  network's only job being to write fresh data into Room — and say plainly that Local Brew uses
  the simpler of the two, and why that's a reasonable tradeoff for this app's size.
- **Where `BuildConfig.GEOAPIFY_API_KEY` actually comes from.** This is a correct real practice
  already in the app, currently unexplained anywhere in the guide: trace `local.properties` (never
  committed) → the `app/build.gradle.kts` `buildConfigField` wiring → generated `BuildConfig` →
  `CafeRepository`'s real usage. Frame as "here's the right way to do this, and Local Brew already
  does it" rather than a gap — re-read the real `build.gradle.kts` for the exact wiring before
  writing this.

## Additional Part 8 gaps (added after the same request, for when Part 8 is drafted)

Add to Part 8's working list above (do not draft Part 8 yet — these are scope notes for when that
page is actually written):

- **Testing the data layer**: unit vs. instrumented tests recap already covers the vocabulary;
  add fake/stub repositories for testing a ViewModel without a real network or database, coroutine
  test dispatchers (`runTest`, `TestDispatcher`), and an in-memory Room database for DAO tests.
  None of this exists in Local Brew today.
- **`DataStore`** for persisted key-value user preferences (Local Brew has no local settings
  persistence today — Firestore/Room cover app data, not user preferences) — mention as the
  modern replacement for `SharedPreferences`.
- **Firestore security rules**: governs who can read/write the data `FirestoreRepository` talks
  to; lives outside the app's own code entirely (in the Firebase console/`firestore.rules`) but is
  essential for anyone building their own Firestore-backed app to know exists at all.
- **Explicit coroutine dispatcher control** (`Dispatchers.IO` via `withContext`): Local Brew's
  suspend functions never specify a dispatcher explicitly, relying on Retrofit/Room's own internal
  defaults — worth naming as a thing a bigger app would want to control explicitly, and briefly
  why (keeping I/O off the main thread is the library's job here, but it's not always).

## Android system lifecycle (mandatory Part 4 section)

Direct user feedback on the first draft of Part 4: it only taught the two real navigation bugs
already fixed in this app (the tab-reset bug and the "Something went wrong" transient flash) —
it never taught navigation and the back stack as genuinely new concepts from scratch, the same
standard Part 1/2/3 are held to. Explicit instruction: "Teach concepts first... Start from
explaining how android life cycle works etc, what happens behind the scenes for various scenarios
for android screens."

This means Part 4 must open with a foundational section on the **Activity lifecycle** — not as an
appendix afterthought (Part 8's original placeholder for this is now removed and links back here
instead), but as genuinely necessary, load-bearing material for understanding what a back stack
even is and why it matters. Position this BEFORE Pass 1 of the three-pass structure below — it's
prerequisite platform knowledge, not library-specific API.

Required content, taught with zero assumed familiarity (a web dev has no equivalent mental model
for this — the closest analogy is a browser tab being suspended/killed by the OS when backgrounded
on mobile Chrome, but say explicitly where that analogy is loose):

- **What an `Activity` actually is** (recall/link to Part 1's definition, don't re-teach) and that
  Local Brew, like most modern Compose apps, has exactly ONE (`MainActivity`) for its entire
  lifetime — re-read `MainActivity.kt` fresh from disk to ground this.
- **Every lifecycle callback, individually, each with what triggers it and what code typically
  runs there**: `onCreate` (the Activity is being constructed for the first time in this
  process — this is where `setContent { }` is called, tie to `MainActivity.kt`'s real code),
  `onStart` (becoming visible), `onResume` (gaining foreground focus — user can now interact),
  `onPause` (losing foreground focus but still visible — e.g. a dialog appearing over it, or the
  user switching to another app but this one still partially visible), `onStop` (no longer
  visible at all — user switched apps or hit Home), `onDestroy` (being torn down — either the user
  finished it, OR the OS is reclaiming memory and may recreate it later from scratch).
- **The critical distinction a beginner will not have**: the OS can kill a stopped Activity's
  entire process to reclaim memory, then later recreate it as if fresh (`onCreate` runs again)
  when the user navigates back to it — this is NOT the same as the user quitting the app, and the
  app has to plan for it. This is exactly why `rememberSaveable` (already taught in Part 2) and
  Android's own `onSaveInstanceState` bundle exist — tie explicitly back to the real
  `MainScreen.kt` state that already uses `rememberSaveable` for this reason.
- **Concrete real-world scenarios, each walked through as "which callbacks fire, in what order,
  and why"**: rotating the device, pressing Home, switching to another app and back quickly,
  switching to another app and leaving it long enough for the OS to reclaim memory, and receiving
  a system dialog (e.g. the location permission prompt) over the Activity.
- **The single most important payoff of this whole section, stated explicitly**: navigating
  between Local Brew's own screens (Discover → Detail → back) does NOT trigger ANY of these
  Activity lifecycle callbacks. There is exactly one Activity and one Compose composition running
  the entire time the app is open; `NavController`'s back stack is a second, separate stack that
  exists *inside* that one Activity/composition, tracking which composable is currently being
  shown. A reader who conflates "the back stack" with "the Activity lifecycle" will misunderstand
  everything that follows — make this distinction unmissable, with a visual (e.g. two concentric
  boxes: the Activity's lifecycle as the outer box, the NavController back stack as an inner box
  that changes shape freely without ever touching the outer box's state).
- An Anticipated Questions box, per the standing "First-time depth" rule.

Only once this section is complete does the existing three-pass structure begin (Pass 1: Local
Brew's real destinations from `Screen.kt`, no code; Pass 2: Navigation Compose's own API surface;
Pass 3: real files, including the two real bugs already documented in this project's history).

**This section failed its first attempt — real, direct user feedback, verbatim:** "Your teaching
was very mid. I expected a proper explanation of android life cycle in simple terms, there was a
lot of hallucanitory yap. You explained the topic but it was vague, I would require an end to end
example which makes it simpler to understand." The fix, as a hard requirement, not a suggestion:
**this section must contain one single, concrete, end-to-end scenario presented as an actual
ordered callback trace** — not prose describing that callbacks fire "in some order," an actual
labeled sequence the reader can see, e.g.:

```
User rotates the phone:
  onPause()      — losing foreground focus, still fully visible for a moment
  onStop()       — no longer visible at all
  onDestroy()    — this Activity instance is being thrown away entirely
  onCreate()     — a BRAND NEW instance is built from scratch (setContent runs again)
  onStart()      — becoming visible again
  onResume()     — regains foreground focus, interactive again
```
Contrast this immediately with a second real trace for a DIFFERENT scenario (e.g. switching to
another app briefly, with no memory reclaim) so the reader sees the two side by side and
understands why they differ (`onDestroy`/`onCreate` present vs. absent). Do not stop at describing
what each callback means in isolation — the payoff is the READER being able to predict, for a
scenario they haven't seen yet, which trace it would produce.

## Build order vs. reading order

These can differ. The pilot page is Part 2 (it establishes the visual style and the first big
interactive piece), even though a reader would encounter Part 1 first. Consequence: if Part 2
needs a language construct or concept that Part 1 will eventually own, teach it fully in Part 2
anyway (don't leave a forward-reference to an unpublished page) — then when Part 1 is actually
written, have it own the canonical explanation and let Part 2 be tightened to a backlink, if
that's still the right call at the time. Never ship a page that says "as explained in Part X"
for a Part X that isn't published yet.

## Before writing ANY code sample: re-read the real file from disk

Never quote a file's contents from conversation memory — this codebase has been edited many
times across a long session, and memory of "what AuthRepository.kt looks like" can be stale.
Read the actual current file immediately before using it in a page.

## The per-file template (Parts 3, 4, 5, 7 — every source file introduced)

Code comes LAST. Every file gets, in this order:

1. **No-code system design first.** Why this file exists, what problem it solves, what
   alternative was considered/rejected and why (cite the real decision if one was made in this
   project — e.g. combined `Cafe` class vs. separate Entity+mapper, or why `AuthRepository` and
   `CafeRepository` are separate files). Pure prose, zero syntax.
2. **Libraries this file needed, and why.** Trace to the actual `libs.versions.toml` /
   `build.gradle.kts` line(s), current as of the on-disk file. Explain why that library/approach
   was chosen if there was a real decision (e.g. KSP vs. kapt for Room).
3. **Manifest tweaks this file required, and why.** Attribute every `AndroidManifest.xml` line
   back to the specific file(s) that needed it. Never leave a manifest entry unexplained.
4. **Code + syntax walkthrough**, per every rule in this document (constructs, timing, first-time
   depth, analogies, visuals).

## Language-construct callouts (mandatory, first time each appears)

**This list is a floor, not a ceiling — it has already had a real gap found in it (see below),
and the same failure mode will happen again if you only check names off a fixed list.** Before
publishing any page, separately ask: "is there any word in this code sample or its explanation
that a web developer with no JVM/Android background would not already know?" — Kotlin/JVM
standard-library terms (like `Serializable`) are just as unfamiliar to that reader as an Android
API, and are just as much in scope for a callout, even when they aren't Android-specific.

**Concept vs. syntax trick — teach the concept first, always.** A known failure mode from this
guide's own history: Part 2 explained *trailing lambda syntax* (the calling-convention trick of
writing `foo(x) { ... }` instead of `foo(x, { ... })`) without ever having first explained what a
lambda fundamentally *is*. A reader who doesn't already know the word "lambda" got the syntax
shortcut for a concept they were never given. The fix: whenever a construct has both (a) a
foundational "what is this" explanation and (b) a syntax convenience/shortcut built on top of it,
(a) must be taught first, in its own callout, before (b) is introduced. Apply this check to every
item below, not just lambdas.

The FIRST time any of these shows up in a code sample anywhere in the guide, stop and explain
what it is in plain terms, with an example, before moving on — even if it feels obvious:
- `class` vs `data class`
- `abstract class`
- `object` (singleton declaration) vs a regular class instance
- `sealed class` / `sealed interface`
- `interface`
- `enum class`
- extension functions
- `companion object`
- **lambda, as a concept** — a nameless function value you can pass around (closest analogy: a
  JS arrow function passed inline, e.g. `array.map(x => x * 2)`) — taught BEFORE:
- trailing-lambda syntax (the `foo(x) { ... }` calling-convention trick, built on top of already
  knowing what a lambda is) — already taught once for `remember(key) { ... }`; reuse that
  explanation, but only after the concept itself has been introduced on that same page.
- `Serializable` — a marker meaning "this object can flatten itself to bytes and rebuild itself
  later" (closest analogy: what `JSON.stringify`/`JSON.parse` solve, though the mechanism differs
  — JVM bytecode-level, not human-readable text). Already explained once in Part 2's `MainTab`
  enum example; reuse that explanation.
- `Parcelable` — Android's own faster alternative to `Serializable`, for the same "survive being
  torn down and rebuilt" problem, used constantly by the OS (rotation, backgrounding). Already
  explained alongside `Serializable` in Part 2; reuse that explanation.

After the first explanation, later appearances just link back to it ("recall from Part 1 what
an `object` is") — don't re-teach it every time. Track in "Status tracking" below which page
ended up owning each construct's first explanation, so later pages know what to link to instead
of guessing.

## The "when does this actually run/render" lens (mandatory, threaded throughout)

Not a one-off section — whenever a class, object, or composable is introduced, say when it
actually gets constructed/executed, not just where it's declared:

- `Graph.kt`'s `by lazy` properties: `AuthRepository()` is NOT constructed when `Graph` loads —
  it's constructed the first time something actually reads `Graph.authRepository`, and never
  again after that (cached).
- Every composable: initial composition vs. recomposition vs. disposal are three genuinely
  different moments — say which one a given line of code runs on.
- `remember` vs `rememberSaveable` vs a plain `var`: frame in terms of WHEN each value is
  created, WHEN it's read from cache vs. recomputed, and WHEN it's thrown away — reuse the
  "sticky note" / "room being demolished" mental model already validated with the user.
- The back stack, specifically: walk through, with an actual snapshot of the stack list, what
  recomposes and when at each of: tapping a card (push), the screen settling, tapping Back (pop
  initiated), the transient gap before the pop finishes, and the pop completing. Reuse the real
  `["Main"]` → `["Main","Detail"]` → `["Main"]` walkthrough and the `previousBackStackEntry`
  timing-gap explanation already built and validated with the user — don't re-derive it from
  scratch, it already works and was confirmed to land well.

## First-time depth (mandatory — the whole guide is held to this standard)

The reader gets exactly one first encounter with each concept, with no chance to ask a
follow-up. Write every first encounter accordingly:

- **Never name a group of behaviors without individually explaining each one.** If a construct
  grants several things at once (e.g. `data class` auto-generating several functions), every
  single one gets its own "what it does, why it exists, an example, a scenario" — never a
  one-line list of names. See "Zero assumed familiarity" above — this was a real, caught gap.
- **First-time examples are invented, never from the app.** See the generic-first rule above.
- **Never one example.** Give multiple distinct scenarios that each exercise the concept
  differently — not the same example reworded. E.g. for `remember` vs. `rememberSaveable`,
  don't stop at the tab/location bugs already fixed in this app — also show a case where plain
  `remember` is perfectly fine and doesn't reset (ordinary recomposition, e.g. text changing on
  screen) side by side with a case where it does reset (the composable being disposed entirely),
  so the reader sees the actual boundary, not just one side of it.
- **Cover edge cases and "gotchas" up front**, not as a later footnote — e.g. when teaching
  `LaunchedEffect`, show what happens if the key never changes vs. changes every recomposition
  (the classic infinite-relaunch mistake), since that's exactly the kind of thing that otherwise
  resurfaces as a confused follow-up question.
- **An "Anticipated Questions" box after every major concept** — a short, concrete FAQ
  preemptively answering the 2-3 things a reader would naturally ask next. This is the actual
  mechanism for "explain it so I don't have to ask again," not just a vague aspiration.
- **Pair every scenario with a visual where one adds clarity** — doesn't have to be one of the
  two big interactive pieces; a simple static before/after diagram, a labeled snapshot, or a
  small table is enough for most concepts. Err toward showing state changes, timelines, and data
  flow rather than only describing them in prose.
- **Self-check before publishing a page:** would a reader who read only this page, with no
  chance to ask a follow-up, walk away with a correct and complete mental model, including the
  edge cases? If you can imagine a follow-up question, its answer belongs on the page already.

## Web-dev analogies

Thread throughout, not just in Part 1 — whenever a concept has a clean web equivalent, name it
(React function components, useState/useMemo, useEffect, React Router / client-side routing,
WebSockets vs. polling, Auth0/Clerk-style auth-as-a-service, IndexedDB/localStorage,
package.json vs. Gradle). Prefer "closest analogy: X" over forcing a 1:1 comparison where the
abstraction genuinely differs — say explicitly where the analogy breaks down too.

## Interactive visuals — build these deliberately, not everywhere

Two confirmed, high-value interactive pieces:

1. **State storage comparison** (Part 2): clickable "recompose" / "navigate away & back"
   buttons that visually show a plain `var`, `remember`, and `rememberSaveable` value either
   surviving or resetting, live.
2. **Back stack pusher/popper** (Part 4): a clickable stack visualizer modeling the real
   Main → Detail → back sequence from this app, including the transient timing-gap moment.

A third candidate exists for Part 6 (trace-a-click end-to-end diagram) — build it only once
Parts 2-4 are done and it's clear what layers actually need showing. Don't invent additional
interactive pieces beyond these three without checking in — a static diagram or table is
usually the right call for a single concept (see "First-time depth" above).

## Visual identity: the guide's own design vs. the app's

The guide gets its own deliberate, consistent design system across all 8 pages (one palette,
one type pairing, consistent header/nav chrome) — load the `artifact-design` skill before
writing any page and follow its light/dark-theme and mobile-readability requirements, since code
samples run long and this will be read on a phone sometimes.

Keep that design system visually distinct from Local Brew's own app theme, so a reader never
confuses "this is the guide's chrome" with "this is a screenshot of the app." The one exception
is Part 7 (Theming): when explaining the app's actual colors, show its real hex values as
clearly labeled swatches/data (e.g. a small table or color chip with the hex code printed), not
restyled as if they were the guide's own theme.

## Per-page authoring process

1. Draft a section-by-section outline first (headings + one line each on what it covers).
2. Check the outline against every section of this file — Part order, per-file template if
   applicable, language constructs that will appear, timing/rendering moments, first-time-depth
   scenarios, analogies, visuals, nav chrome.
3. Only then write full content, following the checked outline.
4. Before publishing, run the "First-time depth" self-check above.
5. Update "Status tracking" below with the real published URL and any constructs/decisions this
   page ended up owning.

## Formatting / style carried over from this session

- Socratic pacing: pose the problem before naming the tool/API that solves it.
- Every new method/function call: distinguish what's purely local (in-memory, no I/O) from what
  does something real (network, disk, GPS) — same standing rule as the rest of this project.
- No filler, no unearned enthusiasm, no restating the question back before answering it.

## Codebase browser (not a teaching page — a reference tool)

A separate, final section on the hub, added at the user's direct request: an interactive
IDE-style file browser (file tree on the left, syntax-highlighted code on the right) of Local
Brew's real source, for viewing on a machine without Android Studio installed. Published at
`https://claude.ai/code/artifact/563d2981-056c-46c1-baa4-82e9f7d882b3`.

**Hard rule if this is ever regenerated or extended:** it must NEVER include `local.properties`
(API key), `key.properties`/`*.jks` (signing keystore + passwords), or `app/google-services.json`
(Firebase config, kept out of the public GitHub repo for the same reason) — this page is publicly
viewable. Only real source/config files belong: `app/src/main/java/**/*.kt`,
`app/src/{test,androidTest}/java/**/*.kt`, `app/src/main/res/{values,xml,drawable}/*.xml`,
`app/src/main/keepRules/*.keep`, `app/src/main/AndroidManifest.xml`, `app/build.gradle.kts`,
root `build.gradle.kts`/`settings.gradle.kts`/`gradle.properties`, `gradle/libs.versions.toml`.
Before publishing any update, grep the generated data blob for the real Geoapify key value and
the keystore password to confirm nothing leaked.

None of the teaching rules above (three-pass structure, generic-first examples, first-time depth)
apply to this page — it's a raw reference tool, not a lesson.

## Status tracking

Update as pages are actually published. Do not mark a Part done until it satisfies every rule
above that applies to it.

| Part | Status | URL | Constructs/decisions this page owns |
|---|---|---|---|
| Hub — "The Ultimate Guide" | Published | https://claude.ai/code/artifact/8e4c53de-8ace-40fc-b359-42f79e143aec | — |
| 0 — Orientation | Not started | — | — |
| 1 — General Android/app-dev concepts + Kotlin fundamentals | Published (revised for depth + generic-first examples per direct user feedback) | https://claude.ai/code/artifact/872dff11-9967-485e-bac4-85a2c721f500 | SDK/JDK/JVM, APK/AAB, Gradle, `compileSdk`/`minSdk`/`targetSdk`, `AndroidManifest.xml` (incl. manifest merging), `Application`/`Activity`/`Context`, build variants + R8, ADB/Logcat, `val`/`var`, null safety, `class` vs `data class` (canonical — all 5 generated functions individually explained), **`sealed class` (canonical)**, **`enum class` (canonical)**, **`object` singleton + `by lazy` timing (canonical)**, **lambda as a concept + trailing-lambda syntax (canonical)**, extension functions, coroutines vs async/await. Every construct now leads with an invented example; real app code moved to a closing pointer in each section. |
| 2 — Compose fundamentals | Published (trimmed to backlink Part 1) | https://claude.ai/code/artifact/ab5cc5e0-d3a7-439e-9175-0e19dde7691d | `Serializable` + `Parcelable` (still owned here, tied to the `MainTab`/`rememberSaveable` bug). `enum class`, `sealed class`, `object`, lambda, and trailing-lambda syntax are now trimmed to backlinks — Part 1 owns their canonical explanations; this page keeps only their specific application to `MainTab`/`LocationPermissionState`/`remember`. |
| 3 — Backend & data layer | Published (rebuilt with mandatory three-pass structure per direct user feedback — see "Teaching a library or framework's API for the first time"; further updated to add the mandatory MVVM section and the three additional Part 3 gaps) | https://claude.ai/code/artifact/3b2c4e84-7418-4ab9-855d-c38a4c58e155 | Pass 1: no-code overview of `Graph` + all 3 repositories' jobs. **New MVVM section (positioned after Pass 1, before Pass 2):** Model/View/ViewModel/Repository defined and named as this app's architecture pattern (canonical — first place in the guide MVVM is named), Repository explicitly framed as a standard refinement of classic 3-layer MVVM rather than a deviation, a Layer→real-class(es)→job→React-equivalent table anchored on `AuthViewModel`/`DiscoverViewModel`/`SearchViewModel`/`DetailViewModel`/`FavoritesViewModel`/`ProfileViewModel`, the one-way View→ViewModel→Repository→(Retrofit/Room/Firestore) dependency diagram, the why-separate-layers reasoning, FAQ box. Does not contrast MVC/MVP/MVI (forward-pointer to Part 8, per spec). Pass 2 (generic, library-level): Retrofit/Gson full API (incl. `interface` canonical via invented `Greeter`, and `reflection` canonical, first appearance anywhere in the guide), Room full API (incl. `abstract class` canonical via invented `Shape`, KSP vs. reflection contrast), Firebase Auth (`Task<T>` + `.await()` bridge), Firestore (one-shot vs. `addSnapshotListener`/`callbackFlow`, 3-way comparison table). Pass 3 (real files): `Cafe` combined-class pros/cons table, Retrofit-ignores-Kotlin-default-values bug + fix, Discover-only caching scenario table, `Graph.kt`'s `by lazy` construction timing traced step-by-step, manifest-merging explanation (why no explicit `INTERNET` permission), manual DI vs. Dagger/Hilt tradeoff (forward-pointer to Part 8). **Three new gap subsections (end of Pass 3):** generic type parameters `<T>` named/explained for the first time anywhere in the guide (light-touch, TypeScript-generic analogy, via invented `sealed class UiState<T>` built on Part 1's canonical `sealed class`) contrasted against the real `CafeFetchResult`/`try`-`catch` pattern; "cache as fallback" (Local Brew's real network-first `CafeRepository` behavior) vs. Room-as-single-source-of-truth/offline-first contrasted honestly; the real `local.properties` → `app/build.gradle.kts` `buildConfigField` → generated `BuildConfig` → `CafeRepository.kt` wiring for `GEOAPIFY_API_KEY`, framed as an existing correct practice. Applies but does not re-teach Part 1's `suspend`/coroutines, `object`, `data class`, `sealed class`. |
| 4 — Navigation & the back stack | Published (second rebuild — fixed two named failures still present after the first rebuild: the lifecycle section lacked a concrete end-to-end callback trace, and Pass 2 folded `NavController`/`NavBackStackEntry` into other sections instead of giving each its own full treatment; see the "failed its first attempt" blocks under "Android system lifecycle" and "Teaching a library or framework's API for the first time") | https://claude.ai/code/artifact/cb9c0dee-b9e1-4006-9745-1ec13408cb42 | **Android system lifecycle (canonical, first and only full explanation anywhere in the guide; Part 8's appendix links back here):** recap-links to Part 1's `Activity` definition, grounds "Local Brew has exactly one Activity for its whole life" in the real `MainActivity.kt` and its Manifest block; all six lifecycle callbacks individually explained with real triggers, plus a callback-order flow diagram; **a literal Logcat-style side-by-side callback trace** (new) contrasting Scenario A (rotate the phone: `onPause→onStop→onDestroy→onCreate→onStart→onResume`, brand-new instance) against Scenario B (quick app-switch: `onPause→onStop→onStart→onResume`, same instance, no `onDestroy`/`onCreate`), with an explicit "the entire difference is one pair of calls" verdict callout, presented before the generalized 5-row real-scenario table (rotation/Home/quick-switch/switch-with-memory-reclaim/system-dialog) which now frames itself as "predict the trace yourself first"; the OS-kill-vs-user-quit distinction tied to Part 2's `rememberSaveable`/`onSaveInstanceState`; the browser-tab analogy stated and qualified; the payoff shown with the live interactive two-concentric-boxes widget (Activity instance counter vs. NavController back stack); an Anticipated Questions box. **Three-pass structure for Navigation Compose:** Pass 1 — Local Brew's real 5 destinations from `Screen.kt`, no code. **Pass 2 — restructured into 8 explicit API checklist cards** (`NavController`, `rememberNavController()`, `NavHost`, `composable()`, `navController.navigate()`, `navController.popBackStack()`, `NavBackStackEntry`, `SavedStateHandle`), each answering, by name: is it a class/interface/function, how you actually get an instance (`NavController` never constructed directly — only via `rememberNavController()`; `NavBackStackEntry` never constructed by app code — handed to you as `composable(route) { entry -> }`'s lambda parameter), its real signature (every parameter/return type, incl. `popBackStack()`'s real `Boolean` and `SavedStateHandle`'s `set`/`get`/`getStateFlow`), and a worked example with real resulting behavior (back-stack-list before/after tables) — via the fully generic invented two-destination notes app, independent of any Local Brew file; `NavController` and `NavBackStackEntry` now each get their own dedicated card instead of being folded into `rememberNavController()`/`composable()`'s prose; owns the generic `currentBackStackEntry`/`previousBackStackEntry` asymmetry explanation; Anticipated Questions box closing Pass 2. Pass 3 — real `NavGraph.kt` walkthrough with the full per-file template, the real `SavedStateHandle` `Cafe` hand-off, the relocated-and-preserved interactive back-stack push/pop visualizer, the "Something went wrong" timing bug (full `["Main","Detail"]`→`["Main"]` snapshot table, `remember(backStackEntry)` fix explained mechanistically), and the navigation-structure cause of the `MainTab`/location disposal bug. Does not re-teach `sealed class`/`object` (Part 1 canonical), `Parcelable` (Part 2 canonical), or generic type parameters `<T>` (Part 3 canonical). |
| 5.1 — Auth (Signup & Login) | Published (first of 4 planned Part 5 sub-pages; build-along structure per "Part 5 — Screen-by-screen build-along" section) | https://claude.ai/code/artifact/ffad71ff-ee5e-4209-86e4-df605b9a180f | First page in this guide to teach real UI-building vocabulary, per Part 5's own spec section. **Canonical (first appearance anywhere in the guide) — later pages, including Part 8, should link back here instead of re-teaching:** `Modifier` (the interface itself, extension-function chaining, immutability, and the order-matters gotcha — flagged during authoring as a real gap: never taught in Parts 1-4 despite being used there implicitly) plus its real functions used here (`fillMaxSize`, `fillMaxWidth`, `padding`, `size`, `background`, `safeDrawingPadding`); `OutlinedTextField` (full parameter treatment: `value`, `onValueChange`, `label`, `keyboardOptions`, `visualTransformation`); `KeyboardOptions`/`KeyboardType`; `PasswordVisualTransformation`; `CircularProgressIndicator`; `TextButton`; `viewModelScope` (where it comes from, why it's used instead of a bare coroutine scope, auto-cancellation on `onCleared()`); `private set` (property with a public getter/private setter, via an invented `BankAccount` example, applied to `AuthViewModel.uiState`); the `.let` scope function (via `?.let`, combined with Part 1's already-canonical safe-call `?.`) — explicitly flagged for Part 8: when the appendix's scope-functions entry (`apply`/`let`/`run`/`with`/`also`) is drafted, it must link back to this page for `.let` rather than re-teaching it; and **`viewModel()` itself (androidx.lifecycle.viewmodel.compose)** — the page's central construct, given the full class/interface/function + how-you-get-an-instance + real-signature + worked-example checklist treatment already established in Part 4 for `NavController`, including where the returned instance actually lives (`ViewModelStoreOwner`/`ViewModelStore`, resolved for every Local Brew screen to that route's own `NavBackStackEntry` from Part 4) and a side-by-side trace (reusing Part 4's Logcat-trace visual pattern) contrasting "rotate the phone" (ViewModel instance survives) against "successfully log in, popUpTo pops the entry" (ViewModel instance destroyed, `onCleared()`/`viewModelScope` cancellation). Applies without re-teaching: `remember`, `data class`/`.copy()`, `sealed class`/`object`/lambda/trailing-lambda (Part 1), `by mutableStateOf` delegation and `LaunchedEffect` (Part 2 canonical), `abstract class`/`interface`/generic `<T>`/`AuthRepository`'s `Task<T>`/`.await()` bridge (Part 3 canonical), `Graph` singleton DI reasoning (Part 3), and `NavController`/`composable()`/`navigate()`/`popUpTo(...){ inclusive = true }`/`NavBackStackEntry` navigation mechanics (Part 4 canonical) — `NavGraph.kt`'s Login/Signup wiring is shown again for continuity but explicitly marked as reinforcement of Part 4's existing full walkthrough, not a new exposure. Full file-by-file, line-by-line attribution tables for `AuthViewModel.kt`, `LoginScreen.kt`/`SignupScreen.kt`, and `AuthForm.kt` per the Part 5 mandate. |
| 5.2 — Main shell | Not started | — | — |
| 5.3 — Discover + Detail | Not started | — | — |
| 5.4 — Search + Favorites + Profile | Not started | — | — |
| 6 — End-to-end system design | Not started | — | — |
| 7 — Theming & custom icons | Not started | — | — |
| 8 — Appendix: must-know Android concepts beyond this app | Not started | — | — |
