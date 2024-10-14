def repoUrl = 'https://github.com/alenabraham-qure/sanity-check-app.git'
def branchesToBuild = 'main|feature/.*'

def productDirs = ['backend', 'frontend']

productDirs.each { product ->
    multibranchPipelineJob("test-${product}") {
        branchSources {
            branchSource {
                source {
                    git {
                        remote(repoUrl)
                        includes(branchesToBuild)
                        // Proper use of withTraits for branch discovery and other traits
                        traits {
                            // Branch discovery strategy
                            branchDiscovery {
                                strategyId(1)  // Discover all branches
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
