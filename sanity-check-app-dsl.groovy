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
                    regexBranchDiscovery {
                        regex(branchesToBuild)
                    }
                }
            }
        }

        orphanedItemStrategy {
            discardOldItems {
                daysToKeep(7)  // Keep builds for 7 days
                numToKeep(5)   // Keep last 5 builds
            }
        }

        triggers {
            periodic(1)  // Scan the repository every 1 minute
        }
    }
}
