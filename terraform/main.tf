provider "google" {
  project = "115175339802"
  region  = "us-central1"
  zone    = "us-central1-c"
}

resource "google_compute_network" "vpc_network" {
  name                    = "terraform-network"
  auto_create_subnetworks = "true"
}


# self-link example https://www.googleapis.com/compute/v1/projects/foo/zones/us-central1-c/instances/terraform-instance
resource "google_compute_instance" "vm_instance" {
  name         = "terraform-instance"
  machine_type = "e2-micro"

  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-11"
    }
  }

  network_interface {
    # A default network is created for all GCP projects
    # network = "default"
    network = google_compute_network.vpc_network.self_link
    access_config {
    }
  }
}

