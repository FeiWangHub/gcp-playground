# OPT1, create uses with 'count', this resource becomes an array
#resource "google_service_account" "demo-users" {
#  count = length(var.user_names)
#  account_id = var.user_names[count.index]
#}
#
#output "svc_accounts0_id" {
#  value = google_service_account.demo-users[0].account_id
#}
#
#output "svc_accounts_all_ids" {
#  value = google_service_account.demo-users[*].account_id
#}

# OPT2, create users with for_each, it's still one tf resource element.
# (safer when deleting one of element in array;)
resource "google_service_account" "demo-users" {
  for_each = toset(var.user_names)
  account_id = each.value
}

output "svc_accounts_all_ids" {
  value = values(google_service_account.demo-users)[*].account_id
}