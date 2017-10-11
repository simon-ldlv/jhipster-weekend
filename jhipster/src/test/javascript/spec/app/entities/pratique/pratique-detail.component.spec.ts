/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ProjetWeekendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PratiqueDetailComponent } from '../../../../../../main/webapp/app/entities/pratique/pratique-detail.component';
import { PratiqueService } from '../../../../../../main/webapp/app/entities/pratique/pratique.service';
import { Pratique } from '../../../../../../main/webapp/app/entities/pratique/pratique.model';

describe('Component Tests', () => {

    describe('Pratique Management Detail Component', () => {
        let comp: PratiqueDetailComponent;
        let fixture: ComponentFixture<PratiqueDetailComponent>;
        let service: PratiqueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ProjetWeekendTestModule],
                declarations: [PratiqueDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PratiqueService,
                    JhiEventManager
                ]
            }).overrideTemplate(PratiqueDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PratiqueDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PratiqueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Pratique(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pratique).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
