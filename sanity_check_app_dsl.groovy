def repoUrl = 'https://github.com/alenabraham-qure/sanity-check-app.git'
def branchesToBuild = 'main|feature/.*'

def productDirs = ['backend', 'frontend']

productDirs.each { product ->
    multibranchPipelineJob("test-${product}") {
        branchSources {
            git {
                remote(repoUrl)
                includes(branchesToBuild)  // Branches to build: main and feature/*
                // Correct use of withTraits for branch discovery and other traits
                withTraits {
                    // Branch discovery strategy
                    branchDiscovery {
                        strategyId(1)  // Strategy for discovering branches (e.g., discover all branches)
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
