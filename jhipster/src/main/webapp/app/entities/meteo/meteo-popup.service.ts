import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Meteo } from './meteo.model';
import { MeteoService } from './meteo.service';

@Injectable()
export class MeteoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private meteoService: MeteoService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.meteoService.find(id).subscribe((meteo) => {
                    if (meteo.updated) {
                        meteo.updated = {
                            year: meteo.updated.getFullYear(),
                            month: meteo.updated.getMonth() + 1,
                            day: meteo.updated.getDate()
                        };
                    }
                    this.ngbModalRef = this.meteoModalRef(component, meteo);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.meteoModalRef(component, new Meteo());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    meteoModalRef(component: Component, meteo: Meteo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.meteo = meteo;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
