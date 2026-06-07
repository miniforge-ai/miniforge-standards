;; Operator spawns a fix workflow in an isolated worktree
(defn spawn-fix-workflow [operator problem]
  (let [worktree-path (str "/tmp/miniforge-fix-" (uuid))
        branch-name (str "fix/" (kebab-case (:description problem)))]

    ;; Create isolated worktree
    (shell "git" "worktree" "add" "-b" branch-name worktree-path "origin/main")

    ;; Start workflow in worktree context
    (start-workflow {:type :fix
                     :context {:cwd worktree-path
                               :branch branch-name}
                     :problem problem})

    ;; Cleanup after merge
    (on-workflow-complete
      (fn [result]
        (shell "git" "worktree" "remove" worktree-path)
        (shell "git" "push" "origin" "--delete" branch-name)))))

;; Worktree metadata map tracked in operator state
{:workflows
 [{:id #uuid "..."
   :type :main
   :worktree "/Users/chris/Local/miniforge.ai/miniforge"
   :branch "feat/user-feature"
   :status :running}

  {:id #uuid "..."
   :type :fix
   :worktree "/tmp/miniforge-fix-abc123"
   :branch "fix/validation-error"
   :status :running
   :spawned-by #uuid "..."}  ; parent workflow

  {:id #uuid "..."
   :type :review
   :worktree "/Users/chris/Local/miniforge.ai/miniforge-cursor"
   :branch "main"
   :status :read-only}]}
