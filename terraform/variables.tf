variable "GCP_PROJECT" {
  description = "GCP project ID"
  type        = string
  default     = "115175339802"
}

variable "regionTW" {
  description = "GCP Asian TW region"
  type        = string
  default     = "asia-east1"
}

variable "JAVA_HOME" {
  description = "System environment value of TF_VAR_JAVA_HOME"
  type        = string
}