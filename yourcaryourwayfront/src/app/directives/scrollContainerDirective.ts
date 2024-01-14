import { Directive, Input, ElementRef } from '@angular/core';

@Directive({
  selector: '[appScrollContainer]'
})
export class ScrollContainerDirective {
  constructor(public el: ElementRef) { }
}
