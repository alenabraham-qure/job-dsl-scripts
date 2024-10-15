def repoUrl = 'https://github.com/alenabraham-qure/sanity-check-app.git'
def branchesToInclude = 'main, feature/*'  // Use a comma to separate multiple branches

def productDirs = ['backend', 'frontend']

productDirs.each { product ->
    multibranchPipelineJob("test-${product}") {
        branchSources {
            branchSource {
                source {
                    git {
                        remote(repoUrl)
                        traits {
                            // Trait for branch discovery
                            trait('jenkins.branch.BranchDiscoveryTrait') {
                                strategyId(1)  // Discover all branches
                            }
                            // Trait for branch filtering
                            trait('jenkins.branch.WildcardSCMHeadFilterTrait') {
                                includes(branchesToInclude)
                                // Exclude line can be omitted if not needed
                            }
                        }
                    }
                }
            }
        }

        orphanedItemStrategy {
            discardOldItems {
                daysToKeep(7)  // Keep builds for 7 days
                numToKeep(5)   // Keep the last 5 builds
            }
        }

        triggers {
            periodic(1)  // Scan the repository every 1 minute
        }
    }
}