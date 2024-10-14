def repoUrl = 'https://github.com/alenabraham-qure/sanity-check-app.git'
def branchesToInclude = 'main feature/*'

def productDirs = ['backend', 'frontend']

productDirs.each { product ->
    multibranchPipelineJob("test-${product}") {
        branchSources {
            branchSource {
                source {
                    git {
                        remote(repoUrl)
                        // Using the correct traits method for filtering branches
                        traits {
                            // Branch discovery strategy
                            branchDiscovery {
                                strategyId(1)  // Discover all branches
                            }
                            // Branch filtering (include only specified branches)
                            headWildcardFilter {
                                includes(branchesToInclude)
                                excludes('') // Leave empty if you don't want to exclude any branches
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
