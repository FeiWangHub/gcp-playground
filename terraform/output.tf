output "terraformBucketID" {
  value = google_storage_bucket.gcp-playground-terraform.id
}

output "terraformBucketUrl" {
  value = google_storage_bucket.gcp-playground-terraform.url
}

output "terraformBucketStorageClass" {
  value = google_storage_bucket.gcp-playground-terraform.storage_class
}

output "terraformBucketStorageLocation" {
  value = google_storage_bucket.gcp-playground-terraform.location
}