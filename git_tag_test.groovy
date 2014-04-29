if (!args[0]) {
  println "Please enter exist version code in the repository as 1st argument."
  return false
}

if (!args[1]) {
  println "Please enter exist version code in the repository as 2nd argument."
  return false
}

def rollbackTag = args[0]
def newTagName = args[1]

println "git pull".execute().text

def tagsOnGit = []
"git tag".execute().text.eachLine { line ->
  tagsOnGit.add(line)
}

if (!tagsOnGit.grep(rollbackTag)) {
  println "The repository have no " + rollbackTag + " tag."
  return false
}

// confirm the latest version code
// Only new version code
def latestVersion = tagsOnGit.sort{ it.size() == 8 }.reverse()[0]
if (latestVersion >= newTagName){
  println "Your new tag name is not latest one."
  return false
}

def gitCheckoutTag = "git checkout refs/tags/" + rollbackTag
println gitCheckoutTag.execute().text


println "create a new tag named " + newTagName
def gitAddTag = "git tag -a " + newTagName + " -m release" + newTagName
println gitAddTag.execute().text

println "Push created tag to github"
def gitPushTag = "git push origin " + newTagName
println gitPushTag.execute().text

println "finish!!"





