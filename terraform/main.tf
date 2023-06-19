provider "google" {
  project = "115175339802"
  region  = "asia-east1"
  zone    = "asia-east1-b"
}

resource "google_storage_bucket" "gcp-playground-bucket" {
  name     = "gcp-playground-bucket"
  location = "asia-east1"
}

resource "google_storage_bucket_object" "test-main-tf" {
  name   = "test-main.tf"
  source = "../LICENSE"
  bucket = google_storage_bucket.gcp-playground-bucket.name
#  lifecycle {
#    prevent_destroy = true
#  }
}

resource "google_storage_bucket" "gcp-playground-lifecycle-test" {
  name     = "gcp-playground-lifecycle-test"
  location = "asia-east1"

  lifecycle_rule {
    action {
      type = "Delete"
    }
    condition {
      age = 36500
    }
  }
}

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

