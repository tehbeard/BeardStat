workflow "Build" {
  on = "push"
  resolves = ["maven"]
}

action "maven" {
  uses = "LucaFeger/action-maven-cli@765e218a50f02a12a7596dc9e7321fc385888a27"
  args = "clean install"
}
