import { Component, OnInit } from '@angular/core';
import { FlagService } from './flag.service';
import { ProductService, Product } from './product.service';
import { environment } from 'src/environment/environment';

const params = new URLSearchParams(window.location.search);
const userEmail = params.get('email') || 'abc123@gmail.com';
const userKey = userEmail;
const userPlan = params.get('plan') || 'gold';

const userContext = {
  kind: 'user',
  key: userKey,
  email: userEmail,
  plan: userPlan,
  framework: 'angular',
};

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [
    ProductService,
    FlagService,
    { provide: 'ldClientID', useValue: environment.ldClientID },
    { provide: 'ldContext', useValue: userContext },
  ],
})
export class AppComponent implements OnInit {
  private readonly purchaseEnabledFlag: string = 'enable-purchasing';
  product: Product = { name: '', price: 0.0, url: '' };
  purchaseDisabled: boolean = true;

  constructor(
    private flagService: FlagService,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.flagService.getFlagValue(this.purchaseEnabledFlag).then((value) => {
      this.purchaseDisabled = !value;
    });

    this.flagService.subscribeToChanges(this.purchaseEnabledFlag, (value) => {
      this.purchaseDisabled = !value;
    });

    const randomProductId = [1, 2, 3, 4, 5][Math.floor(Math.random() * 5)];
    this.productService
      .getProduct(
        randomProductId,
        userContext.key,
        userContext.plan,
        userContext.email
      )
      .subscribe((product) => (this.product = product));
  }

  getEmail() {
    return userContext.email;
  }

  getPlan() {
    return userContext.plan;
  }

  onPlaceOrderClick() {
    if (!this.purchaseDisabled) {
      alert('Thank you for your purchase.');
    }
  }
}
