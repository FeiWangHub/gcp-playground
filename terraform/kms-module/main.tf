data "google_secret_manager_secret" "pg_user" {
  secret_id = "ElephantSQL-PG-USER"
}

data "google_secret_manager_secret_version" "pg_user_version" {
  secret       = "ElephantSQL-PG-USER"
  version      = "latest"
}

output "GCP_KMS_PG_USER" {
  value = data.google_secret_manager_secret.pg_user.secret_id
}

output "GCP_KMS_PG_USER_CIPHER" {
  sensitive = true
  value = data.google_secret_manager_secret_version.pg_user_version
}