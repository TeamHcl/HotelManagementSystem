---
name: resolve-conflict
description: Use this skill when a feature branch PR has merge conflicts with the base branch (main or master) and needs to be resolved and pushed.
---
# Objective
Safely resolve git merge conflicts on a feature branch PR and prepare the branch for a successful merge into the base branch.

## Branch Detection Rules
- Always detect the base branch dynamically: prefer `main`, otherwise `master`.
- Never assume `main` exists.
- If neither base branch exists locally, check remote refs (`origin/main`, `origin/master`).
- If still unresolved, stop and ask the user which base branch to use.

## Resolution Strategy
Analyze the conflicting files and apply the appropriate strategy:

A. **Structural Conflicts** (e.g., imports, folder changes) → Merge both, ensuring paths are correct.
B. **Additive Conflicts** (e.g., two new methods in the same class) → Keep both additions.
C. **Config Conflicts** (e.g., application.yml, package.json) → Combine carefully, do not arbitrarily overwrite.
D. **Logical Conflicts** (e.g., both changed the same core business logic) → Escalate to the user. Do not guess the intent.

## Workflow Steps
1. Verify the current state and branch:
    - `git status`
    - `git branch --show-current`
2. Ensure you are on the feature/PR branch (not on the base branch).
3. Detect base branch using the Branch Detection Rules.
4. Update base branch:
    - `git checkout <base-branch>`
    - `git pull origin <base-branch>`
5. Return to the feature branch and merge the base into it:
    - `git checkout <feature-branch>`
    - `git merge <base-branch>`
6. If conflicts appear, analyze markers (`<<<<<<<`, `=======`, `>>>>>>>`) and apply strategy (A, B, C, or D).
7. Stage resolved files: `git add .`
8. Finalize the merge with a base-aware message:
    - `git commit -m "merge(<base-branch>): resolve PR conflicts"`
9. Run project validation (for this repo, compile before push):
    - `./mvnw.cmd -DskipTests compile` (Windows)
10. Push the updated feature branch:
    - `git push -u origin HEAD`

## Expected Outcome
- PR branch is updated with the latest base branch changes.
- Merge conflicts are resolved on the feature branch.
- Branch is pushed so the PR can merge into `main` or `master`.

## Constraints
- Do not overwrite changes blindly.
- Ensure the resolution does not break existing APIs or compilation.
- If conflict intent is ambiguous, pause and ask the user before committing.