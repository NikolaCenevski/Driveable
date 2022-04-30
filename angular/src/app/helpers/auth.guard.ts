import { Injectable } from '@angular/core';
import {
  Router,
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from '@angular/router';
import { TokenStorageService } from '../services/token-storage.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private tokenStorageService: TokenStorageService
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentUser = this.tokenStorageService.getUser();
    if (currentUser) {
      if (route.data['role'] && route.data['role'] !== currentUser.userRole) {
        this.router.navigate(['/']);
        return false;
      }

      return true;
    }

    this.router.navigate(['/login']);
    return false;
  }
}
