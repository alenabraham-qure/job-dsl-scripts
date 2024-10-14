def repoUrl = 'https://github.com/alenabraham-qure/sanity-check-app.git'
def branchesToBuild = 'main|feature/.*'

def productDirs = ['backend', 'frontend']

productDirs.each { product ->
    multibranchPipelineJob("test-${product}") {
        branchSources {
            git {
                remote(repoUrl)
                includes(branchesToBuild)  // Branches to build: main and feature/*
                traits {
                    // Use the correct trait for branch discovery
                    branchDiscovery {
                        strategyId(1)  // Strategy for discovering branches
                    }
                    // Other traits can be added here (like PR discovery, etc.)
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
