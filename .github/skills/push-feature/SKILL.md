---
name: push-feature
description: Use this skill to automatically sync a feature branch with the repo base branch (main or master) and execute a git push to the remote repository.
---
# Objective
Safely sync the feature branch with the base branch (main or master) and push it to the remote repository autonomously.

## Execution Steps (USE TERMINAL TOOL)
You must use your terminal execution tool to run these commands sequentially. If any command fails, stop and report the error to the user.

1. Detect base branch by checking in order:
    - `git show-ref --verify --quiet refs/heads/main` -> use `main` when present
    - otherwise `git show-ref --verify --quiet refs/heads/master` -> use `master` when present
    - if neither exists locally, check remotes:
        - `git show-ref --verify --quiet refs/remotes/origin/main`
        - `git show-ref --verify --quiet refs/remotes/origin/master`
    - if no base branch is found, stop and ask the user.
2. Ensure the current branch is a feature branch (not base). If the user is on the base branch, create/switch to a feature branch first.
3. Execute `git checkout <base-branch>`
4. Execute `git pull origin <base-branch>`
5. Execute `git checkout -` (to return to the feature branch)
6. Execute `git merge <base-branch>`
7. If the merge is successful, execute `git push -u origin HEAD`

Do not just suggest the commands. Execute them via the terminal tool.