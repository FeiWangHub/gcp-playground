provider "google" {
  project = var.GCP_PROJECT
  region  = var.regionTW
  zone    = "asia-east1-b"
}

terraform {
  backend "gcs" {
    bucket = "gcp-playground-terraform"
    prefix = "terraform/state"
  }
}

module "kms-module" {
  source = "./kms-module"
}
output "kms-PG-USER" {
  value = module.kms-module.GCP_KMS_PG_USER
}

resource "google_storage_bucket" "gcp-playground-terraform" {
  name     = "gcp-playground-terraform"
  location = var.regionTW
}

#resource "google_storage_bucket_object" "test-main-tf" {
#  name   = "test-main.tf"
#  source = "../LICENSE"
#  bucket = google_storage_bucket.gcp-playground-terraform.name
##  lifecycle {
##    prevent_destroy = true
##  }
#}

#resource "google_storage_bucket" "gcp-playground-lifecycle-test" {
#  name     = "gcp-playground-lifecycle-test"
#  location = "asia-east1"
#  force_destroy = true
#
#  lifecycle_rule {
#    action {
#      type = "Delete"
#    }
#    condition {
#      age = 36500
#    }
#  }
#}

#resource "google_compute_network" "vpc_network" {
#  name                    = "terraform-network"
#  auto_create_subnetworks = "true"
#}
#
#
## self-link example https://www.googleapis.com/compute/v1/projects/foo/zones/us-central1-c/instances/terraform-instance
#resource "google_compute_instance" "vm_instance" {
#  name         = "terraform-instance"
#  machine_type = "e2-micro"
#
#  boot_disk {
#    initialize_params {
#      image = "debian-cloud/debian-11"
#    }
#  }
#
#  network_interface {
#    # A default network is created for all GCP projects
#    # network = "default"
#    network = google_compute_network.vpc_network.self_link
#    access_config {
#    }
#  }
#}
