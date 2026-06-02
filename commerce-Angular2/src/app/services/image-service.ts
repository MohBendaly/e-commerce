import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ImageService {
  private basePath = '/assets/images/';
  private defaultImage = 'default/default-image.jpg';

  getProductImageUrl(imageName: string): string {
    if (!imageName) {
      return this.basePath + this.defaultImage;
    }
    return `${this.basePath}products/${imageName}`;
  }

  handleImageError(event: Event): void {
    const img = event.target as HTMLImageElement;
    img.src = this.basePath + this.defaultImage;
  }
}
